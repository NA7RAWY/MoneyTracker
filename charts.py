from flask import Flask, jsonify, send_file, request
from flask_cors import CORS
from confluent_kafka import Producer, Consumer, KafkaError
import matplotlib.pyplot as plt
from io import BytesIO
import json
import os
import time
import uuid

app = Flask(__name__)
CORS(app)

KAFKA_BOOTSTRAP_SERVERS = os.getenv('KAFKA_BOOTSTRAP_SERVERS', 'localhost:9092')

producer = Producer({
    'bootstrap.servers': KAFKA_BOOTSTRAP_SERVERS
})

consumer = Consumer({
    'bootstrap.servers': KAFKA_BOOTSTRAP_SERVERS,
    'group.id': 'chart-group',
    'auto.offset.reset': 'earliest',  # Changed to earliest
    'enable.auto.commit': False  # Disable auto-commit for manual control
})

consumer.subscribe(['spending-responses'])

def delivery_report(err, msg):
    if err is not None:
        print(f'Message delivery failed: {err}')
    else:
        print(f'Message delivered to {msg.topic()} [{msg.partition()}]')

@app.route('/api/spending-by-category-chart', methods=['GET'])
def spending_by_category_chart():
    user_id = request.args.get('user_id', type=int)
    year = request.args.get('year', type=int)
    month = request.args.get('month', type=int)

    if not user_id or not year or not month:
        return jsonify({'error': 'Missing user_id, year or month parameters'}), 400

    request_id = str(uuid.uuid4())
    request_data = {
        'userId': user_id,
        'year': year,
        'month': month,
        'requestId': request_id
    }

    try:
        producer.produce('spending-requests', key=request_id, value=json.dumps(request_data), callback=delivery_report)
        producer.flush()
    except Exception as e:
        return jsonify({'error': f'Failed to send Kafka message: {str(e)}'}), 500

    # Poll for response
    start_time = time.time()
    timeout = 10  # seconds
    while time.time() - start_time < timeout:
        msg = consumer.poll(1.0)
        if msg is None:
            continue
        if msg.error():
            if msg.error().code() == KafkaError._PARTITION_EOF:
                continue
            else:
                return jsonify({'error': f'Kafka consumer error: {msg.error()}'}), 500

        response_data = json.loads(msg.value().decode('utf-8'))
        if response_data.get('requestId') == request_id:
            spending_by_category = response_data.get('spendingByCategory', {})
            if not spending_by_category:
                consumer.commit(msg)  # Commit offset
                return jsonify({'error': 'No data returned from Spring service'}), 404

            categories = list(spending_by_category.keys())
            amounts = list(spending_by_category.values())

            fig, ax = plt.subplots()
            ax.bar(categories, amounts)
            ax.set_xlabel('Category')
            ax.set_ylabel('Amount')
            ax.set_title(f'Monthly Spending by Category')

            img = BytesIO()
            plt.savefig(img, format='png')
            plt.close(fig)
            img.seek(0)

            consumer.commit(msg)  # Commit offset after successful processing
            return send_file(img, mimetype='image/png')

    return jsonify({'error': 'Timeout waiting for response'}), 504

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5000)
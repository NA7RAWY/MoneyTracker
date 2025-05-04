import requests
from flask import Flask, jsonify, send_file, request
import matplotlib.pyplot as plt
from io import BytesIO
from flask_cors import CORS

app = Flask(__name__)
CORS(app)
SPRING_API_URL = "http://localhost:8080/api/transactions/user/{user_id}/spending-by-category"

@app.route('/api/spending-by-category-chart', methods=['GET'])
def spending_by_category_chart():
    user_id = request.args.get('user_id', type=int)
    year = request.args.get('year', type=int)
    month = request.args.get('month', type=int)

    if not user_id or not year or not month:
        return jsonify({'error': 'Missing user_id, year or month parameters'}), 400

    response = requests.get(SPRING_API_URL.format(user_id=user_id), params={'year': year, 'month': month})
    
    if response.status_code != 200:
        return jsonify({'error': 'Failed to fetch data from Spring API'}), 500
    
    expense_data = response.json()  
    
    categories = list(expense_data.keys())
    amounts = list(expense_data.values())

    fig, ax = plt.subplots()
    ax.bar(categories, amounts)

    ax.set_xlabel('Category')
    ax.set_ylabel('Amount')
    ax.set_title(f'Monthly Spending by Category')

    img = BytesIO()
    plt.savefig(img, format='png')
    img.seek(0)

    return send_file(img, mimetype='image/png')

if __name__ == '__main__':
    app.run(debug=True)
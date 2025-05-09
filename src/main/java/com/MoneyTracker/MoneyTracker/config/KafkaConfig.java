package com.MoneyTracker.MoneyTracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.apache.kafka.clients.admin.NewTopic;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic spendingRequestsTopic() {
        return TopicBuilder.name("spending-requests")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic spendingResponsesTopic() {
        return TopicBuilder.name("spending-responses")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
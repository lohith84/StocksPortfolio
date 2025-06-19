package org.example.portfolio.config;

import org.example.portfolio.dto.TradeRequestDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

@Configuration
public class KafkaListenerConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TradeRequestDTO> kafkaListenerContainerFactory(
            ConsumerFactory<String, TradeRequestDTO> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, TradeRequestDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
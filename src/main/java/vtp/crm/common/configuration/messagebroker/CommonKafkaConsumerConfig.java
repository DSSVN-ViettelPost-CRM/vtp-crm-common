package vtp.crm.common.configuration.messagebroker;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Slf4j
public class CommonKafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${kafka.consumer.group-id}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(DefaultErrorHandler errorHandler, ConsumerFactory<String, String> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(errorHandler);
        return factory;
    }

    @Bean
    public DefaultErrorHandler errorHandler(
            @Value("${spring.kafka.consumer.error.handler.retry-interval}") Long interval,
            @Value("${spring.kafka.consumer.error.handler.max-attempts}") Long maxAttempts) {
        BackOff fixedBackOff = new FixedBackOff(interval, maxAttempts);
        return new DefaultErrorHandler((consumerRecord, e) -> {
            // logic to execute when all the retry attemps are exhausted
            log.error("[Kafka-Consumer-Error] Process message failed {} times", maxAttempts);
            log.error("[Kafka-Consumer-Error] Error on processing message: topic={}, partition={}, headers={}, key={}, value={} ",
                    consumerRecord.topic(), consumerRecord.partition(), consumerRecord.headers(), consumerRecord.key(), consumerRecord.value());
        }, fixedBackOff);
    }
}

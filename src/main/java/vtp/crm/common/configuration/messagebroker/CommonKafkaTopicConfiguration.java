package vtp.crm.common.configuration.messagebroker;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonKafkaTopicConfiguration {

    @Value("${spring.kafka.topic.sync-org.name}")
    private String SYNC_ORG_TOPIC;

    @Value("${spring.kafka.topic.sync-org.num-partitions}")
    private Integer SYNC_ORG_NUM_PARTITIONS;

    @Value("${spring.kafka.topic.sync-org.replication-factor}")
    private Short SYNC_ORG_REPLICATION_FACTOR;

    @Bean
    public NewTopic syncOrgTopic() {
        return new NewTopic(SYNC_ORG_TOPIC, SYNC_ORG_NUM_PARTITIONS, SYNC_ORG_REPLICATION_FACTOR);
    }

}

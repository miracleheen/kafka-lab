package sverdlov_dev.order_service;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, Order> producerFactory() {
        Map<String, Object> configProperties = new HashMap<>();
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        JacksonJsonSerializer<Order> serializer = new JacksonJsonSerializer<>();
        serializer.setAddTypeInfo(false);

        return new DefaultKafkaProducerFactory<>(
                configProperties, //config
                new StringSerializer(), //сериализация для string
                serializer//это сериализация для order (object)
        );
    }

    @Bean
    public KafkaTemplate<String, Order> kafkaTemplate(
            ProducerFactory<String, Order> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }
}

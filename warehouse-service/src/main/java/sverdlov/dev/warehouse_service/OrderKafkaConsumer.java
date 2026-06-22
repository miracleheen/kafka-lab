package sverdlov.dev.warehouse_service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderKafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(OrderKafkaConsumer.class);

    @KafkaListener(topics = "order-topic")
    public void consumeOrder(ConsumerRecord<String, Order> record) {
        log.info(
                "Received order: order={}, key={}, partition={}",
                record.value(),
                record.key(),
                record.partition()
        );
    }
}

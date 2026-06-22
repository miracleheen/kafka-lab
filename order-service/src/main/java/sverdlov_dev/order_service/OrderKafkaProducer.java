package sverdlov_dev.order_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderKafkaProducer {
    private final KafkaTemplate<String, Order> kafkaTemplate;
    private static final Logger log = LoggerFactory.getLogger(OrderKafkaProducer.class);

    @Autowired
    public OrderKafkaProducer(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderToKafka(Order order) {
        kafkaTemplate.send("order-topic", order)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Kafka send failed", ex);
                    } else {
                        log.info("Kafka acked: topic={}, partition={}, offset={}",
                                result.getRecordMetadata().topic(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });
    }
}

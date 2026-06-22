package sverdlov_dev.order_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderKafkaProducer orderKafkaProducer;

    @Autowired
    public OrderService(OrderKafkaProducer orderKafkaProducer) {
        this.orderKafkaProducer = orderKafkaProducer;
    }

    public void saveOrder(Order order) {
        //saving to db
        //send to kafka
        orderKafkaProducer.sendOrderToKafka(order);
        log.info("Order successfully saved: id={}", order.orderId());
    }
}

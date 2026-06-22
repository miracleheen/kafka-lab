package sverdlov_dev.order_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;
    private final AtomicInteger orderIdCounter;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
        this.orderIdCounter = new AtomicInteger();
    }

    @PostMapping
    public void createOrder(@RequestBody Order order) {
        log.info("Create order called: order{}", order);

        int orderId = orderIdCounter.incrementAndGet();

        var productName = order.product() +
                ThreadLocalRandom.current().nextInt(100);

        var orderToSave = new Order(
                Integer.toString(orderId),
                productName,
                order.quantity()
        );

        orderService.saveOrder(orderToSave);
    }
}

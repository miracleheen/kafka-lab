package sverdlov_dev.order_service;

public record Order(
        String orderId,
        String product,
        Integer quantity
) {
}

package utils;

public class OrderWrapper {
    private final OrderProcessor processor;
    private final LegacyOrderUtils legacyUtils;

    public OrderWrapper(OrderProcessor processor, LegacyOrderUtils legacyUtils) {
        this.processor = processor;
        this.legacyUtils = legacyUtils;
    }

    public String wrap(String orderId) {
        String code = legacyUtils.makeOrderCode(orderId);
        return processor.updateOrderStatus(code, 1) + "-" + code;
    }
}

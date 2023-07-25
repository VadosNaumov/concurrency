package course.concurrency.m3_shared.immutable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {

    private Map<Long, Order> currentOrders = new HashMap<>();
    private long nextId = 0L;

    private synchronized long nextId() {
        return nextId++;
    }

    public synchronized long createOrder(List<Item> items) {
        long id = nextId();
        Order order = new Order(id, items, null, false, Order.Status.NEW);
        currentOrders.put(id, order);
        return id;
    }

    public synchronized void updatePaymentInfo(long orderId, PaymentInfo paymentInfo) {
        Order order = currentOrders.get(orderId);
        if (order != null) {
            Order updatedOrder = order.withPaymentInfo(paymentInfo);
            currentOrders.put(orderId, updatedOrder);
            if (updatedOrder.checkStatus()) {
                deliver(updatedOrder);
            }
        }
    }

    public synchronized void setPacked(long orderId) {
        Order order = currentOrders.get(orderId);
        if (order != null) {
            Order updatedOrder = order.withPacked(true);
            currentOrders.put(orderId, updatedOrder);
            if (updatedOrder.checkStatus()) {
                deliver(updatedOrder);
            }
        }
    }

    private synchronized void deliver(Order order) {
        /* ... */
        Order updatedOrder = order.withStatus(Order.Status.DELIVERED);
        currentOrders.put(order.getId(), updatedOrder);
    }

    public synchronized boolean isDelivered(long orderId) {
        Order order = currentOrders.get(orderId);
        return order != null && order.getStatus() == Order.Status.DELIVERED;
    }
}

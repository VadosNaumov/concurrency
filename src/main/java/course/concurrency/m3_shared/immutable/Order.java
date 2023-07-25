package course.concurrency.m3_shared.immutable;

import java.util.List;

import static course.concurrency.m3_shared.immutable.Order.Status.NEW;

public class Order {

    public enum Status { NEW, IN_PROGRESS, DELIVERED }

    private final Long id;
    private final List<Item> items;
    private final PaymentInfo paymentInfo;
    private final boolean isPacked;
    private final Status status;

    public Order(Long id, List<Item> items, PaymentInfo paymentInfo, boolean isPacked, Status status) {
        this.id = id;
        this.items = items;
        this.paymentInfo = paymentInfo;
        this.isPacked = isPacked;
        this.status = status;
    }

    public synchronized boolean checkStatus() {
        if (items != null && !items.isEmpty() && paymentInfo != null && isPacked) {
            return true;
        }
        return false;
    }

    public Long getId() {
        return id;
    }

//    public void setId(Long id) {
//        this.id = id;
//    }

    public List<Item> getItems() {
        return items;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

//    public void setPaymentInfo(PaymentInfo paymentInfo) {
//        this.paymentInfo = paymentInfo;
//        this.status = Status.IN_PROGRESS;
//    }

    public boolean isPacked() {
        return isPacked;
    }

//    public void setPacked(boolean packed) {
//        isPacked = packed;
//        this.status = Status.IN_PROGRESS;
//    }

    public Status getStatus() {
        return status;
    }

//    public void setStatus(Status status) {
//        this.status = status;
//    }

    public Order withPaymentInfo(PaymentInfo paymentInfo) {
        return new Order(id, items, paymentInfo, isPacked, Status.IN_PROGRESS);
    }

    public Order withPacked(boolean isPacked) {
        return new Order(id, items, paymentInfo, isPacked, Status.IN_PROGRESS);
    }

    public Order withStatus(Status status) {
        return new Order(id, items, paymentInfo, isPacked, status);
    }
}

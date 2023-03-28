package com.example.emart.entity;

import com.example.emart.entity.enums.ORDER_STATUS;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor
public class Order extends BaseTime {
    @Id @GeneratedValue
    private Long id;
    private String number; // concat(now(), id)

    @Enumerated(EnumType.STRING) // ORDER, CANCEL
    private ORDER_STATUS orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
        user.getOrders().add(this);
    }
    public void addOrderProduct(OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
        orderProduct.setOrder(this);
    }
    public static Order createOrder(User user, String orderNumber, List<OrderProduct> orderProducts) {
        Order order = new Order();
        order.setUser(user);
        order.setNumber(orderNumber);
        for (OrderProduct orderProduct : orderProducts) {
            order.addOrderProduct(orderProduct);
        }
        order.setOrderStatus(ORDER_STATUS.ORDER);
        return order;
    }
    public void cancel() {
        this.setOrderStatus(ORDER_STATUS.CANCEL);
        for (OrderProduct orderProduct : this.orderProducts) {
            orderProduct.cancel();
        }
    }
}

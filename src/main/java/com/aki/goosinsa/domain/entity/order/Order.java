package com.aki.goosinsa.domain.entity.order;

import com.aki.goosinsa.domain.entity.delivery.Delivery;
import com.aki.goosinsa.domain.entity.delivery.DeliveryStatus;
import com.aki.goosinsa.domain.entity.orderItem.OrderItem;
import com.aki.goosinsa.domain.entity.user.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "orders")
@SequenceGenerator(name = "ORDER_SEQ_GEN", sequenceName = "ORDER_SEQ", initialValue = 1, allocationSize = 1)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_SEQ")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Singular("singularOrderItem")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn
    private Delivery delivery;

    @CreationTimestamp
    private LocalDateTime createDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    //==연관관계 편의 메서드==//
    public void setEntityUser(User user){
        this.user = user;
        user.getOrders().add(this);
    }

    public void addEntityOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setEntityOrderItem(this);
    }

    public void setEntityDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setEntityOrder(this);
    }

    // ...  end ==연관관계 편의 메서드==//


    //==생성 메서드==//
    public static Order createOrder(User user, Delivery delivery,  OrderItem... orderItems){
        Order order = new Order();
        order.setEntityUser(user);
        order.setEntityDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addEntityOrderItem(orderItem);
        }
        order.status = OrderStatus.ORDER;
        order.createDate = LocalDateTime.now();
        return order;
    }

    //==비즈니스 로직==//
    public void cancelStatus(OrderStatus status) {
        this.status = status;
    }

    /**
     * 주문 취소
     * */
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능 합니다.");
        }

        this.cancelStatus(OrderStatus.CANCEL);

        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }

    }

    //==조회 로직==//
    /**
     * 전체 상품 전체 가격 조회
     * */
    public int getTotalPrice(){
        int totalPrice = 0;
        for (OrderItem orderItem : this.orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }

        return totalPrice;
    }

}

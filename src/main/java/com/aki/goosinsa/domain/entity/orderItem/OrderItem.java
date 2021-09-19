package com.aki.goosinsa.domain.entity.orderItem;

import com.aki.goosinsa.domain.entity.item.Item;
import com.aki.goosinsa.domain.entity.order.Order;
import com.aki.goosinsa.domain.entity.order.OrderStatus;
import com.aki.goosinsa.domain.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "ORDERITEM_SEQ_GEN", sequenceName = "ORDERITEM_SEQ", initialValue = 1, allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERITEM_SEQ")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    private int orderPrice;
    private int count;

    @Enumerated(EnumType.STRING)
    private OrderItemStatus status;

    public void setEntityOrderItem(Order order){
        this.order = order;
    }

    //== 생성 메서드==//
    public static OrderItem createOrderItem(User user, Item item, int orderPrice, int count){
        return OrderItem.builder()
                .item(item)
                .user(user)
                .orderPrice(orderPrice)
                .count(count)
                .status(OrderItemStatus.READY)
                .build();
    }

    //== 비즈니스 로직 ==//
    public void cancel() {
        getItem().addStock(count);
    }

    public void setOrderItemStatusOrder(){
        this.status = OrderItemStatus.ORDER;
    }

    //== 조회 로직 ==//
    /**
     * 주문상품 전체 가격 조회
     * */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }

    //== 삭제 로직 ==//
    public void deleteOrderItem(){
        this.item = null;
        this.user = null;
        this.order = null;
    }
}

package com.aki.goosinsa.service.order;

import com.aki.goosinsa.domain.entity.order.Order;
import com.aki.goosinsa.domain.entity.order.OrderSearch;

import java.util.List;

public interface OrderService {
    
    /**
     * 주문
     * */
    public Long order(Long userId);

    /**
     * 주문 취소
     * */
    public void cancelOrder(Long orderId);
    
    /**
     * 검색
     * */
    public List<Order> findOrders(OrderSearch orderSearch);
    
}

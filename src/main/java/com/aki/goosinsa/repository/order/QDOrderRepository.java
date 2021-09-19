package com.aki.goosinsa.repository.order;

import com.aki.goosinsa.domain.entity.order.Order;
import com.aki.goosinsa.domain.entity.order.OrderSearch;

import java.util.List;

public interface QDOrderRepository {

    public List<Order> findAllOrderSearch(OrderSearch orderSearch);

    List<Order> findByAllJoin(Long userId);

    public int orderItemListTotalCount(Long userId);
}

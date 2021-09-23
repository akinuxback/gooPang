package com.aki.goosinsa.repository.orderItem;

import com.aki.goosinsa.domain.entity.order.Order;
import com.aki.goosinsa.domain.entity.orderItem.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}

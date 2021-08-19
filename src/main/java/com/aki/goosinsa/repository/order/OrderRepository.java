package com.aki.goosinsa.repository.order;

import com.aki.goosinsa.domain.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

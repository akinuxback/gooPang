package com.aki.goosinsa.domain.entity.order;

import com.aki.goosinsa.domain.entity.order.OrderStatus;
import lombok.Data;

@Data
public class OrderSearch {

    private String username;
    private OrderStatus orderStatus;
}

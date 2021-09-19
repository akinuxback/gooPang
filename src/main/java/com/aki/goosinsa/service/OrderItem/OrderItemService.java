package com.aki.goosinsa.service.OrderItem;

public interface OrderItemService {

    public Long createOrderItem(Long userId, Long itemId, int count);

    public void deleteOrderItem(Long orderItemId);

}

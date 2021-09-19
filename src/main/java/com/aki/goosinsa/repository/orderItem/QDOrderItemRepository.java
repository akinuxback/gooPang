package com.aki.goosinsa.repository.orderItem;

import com.aki.goosinsa.domain.entity.orderItem.OrderItem;

import java.util.List;

public interface QDOrderItemRepository {

    //장바구니 상태 보여줄 리스트
    public List<OrderItem> findListByIdAndStatusReadyJoinItemJoinUser(Long userId);

    // 장바구니 상품 구매 등록시 처리할 로직
    public List<OrderItem> findListByIdAndStatusOrderJoinItemJoinUser(Long userId);

    //구매내역 리스트 보여주기
    List<OrderItem> findListByIdAndStatusOrderJoinItemJoinUserJoinOrder(Long id);

}

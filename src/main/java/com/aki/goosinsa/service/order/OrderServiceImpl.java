package com.aki.goosinsa.service.order;

import com.aki.goosinsa.domain.entity.delivery.Delivery;
import com.aki.goosinsa.domain.entity.item.Item;
import com.aki.goosinsa.domain.entity.order.Order;
import com.aki.goosinsa.domain.entity.order.OrderSearch;
import com.aki.goosinsa.domain.entity.orderItem.OrderItem;
import com.aki.goosinsa.domain.entity.user.User;
import com.aki.goosinsa.exception.NotFoundItemException;
import com.aki.goosinsa.repository.item.ItemRepository;
import com.aki.goosinsa.repository.order.OrderRepository;
import com.aki.goosinsa.repository.order.QDOrderRepository;
import com.aki.goosinsa.repository.orderItem.OrderItemRepository;
import com.aki.goosinsa.repository.orderItem.QDOrderItemRepository;
import com.aki.goosinsa.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Log4j2
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final QDOrderRepository qdOrderRepository;

    private final OrderItemRepository orderItemRepository;
    private final QDOrderItemRepository qdOrderItemRepository;

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    /**
     * 전체 주문
     * */
    @Override
    @Transactional
    public Long order(Long userId) {
        
        // 엔티티 조회
        User user = userRepository.findById(userId).get();
        
        // 장바구니 상품들 가져오기
        List<OrderItem> orderItemList = qdOrderItemRepository.findListByIdAndStatusReadyJoinItemJoinUser(user.getId());
        
        // 장바구니에 상품이 없을경우 예외처리
        if(orderItemList.isEmpty()){
            throw new NotFoundItemException();
        }
        
        // 장바구니에 담긴 아이템의 재고 수량 에서 - 주문한 아이템 만큼의 수량 빼기
        orderItemList.forEach(oi -> {
            oi.getItem().removeStock(oi.getCount());
        });
        

        // 배송정보 생성
        Delivery delivery = new Delivery();

        delivery.setAddress(user.getAddress());
        

        Order order = Order.createOrder(user, delivery, orderItemList.toArray(OrderItem[]::new));
        log.info("=====================       order        ==============================");
        log.info(order.getStatus());
        Order save = orderRepository.save(order);



        log.info("===========================================================");
        log.info(save.getId());
        return order.getId();
    }

    /**
     * 단건 주문
     * */
    @Override
    @Transactional
    public Long orderOne(Long userId, Long orderItemId) {

        // 엔티티 조회
        User user = userRepository.findById(userId).get();

        // 장바구니 상품들 가져오기
        OrderItem orderItem = qdOrderItemRepository.findByIdAndStatusReadyJoinItemJoinUser(orderItemId);

        // 장바구니에 상품이 없을경우 예외처리
        if(orderItem == null){
            throw new NotFoundItemException();
        }

        // 장바구니에 담긴 아이템의 재고 수량 에서 - 주문한 아이템 만큼의 수량 빼기
        orderItem.getItem().removeStock(orderItem.getCount());



        // 배송정보 생성
        Delivery delivery = new Delivery();

        delivery.setAddress(user.getAddress());


        Order order = Order.createOrder(user, delivery, orderItem);
        log.info("=====================       order        ==============================");
        log.info(order.getStatus());
        Order save = orderRepository.save(order);



        log.info("===========================================================");
        log.info(save.getId());
        return order.getId();
    }

    /**
     *  주문 취소
     * */
    @Transactional
    @Override
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.cancel();
    }

    /**
     * 검색
     * */
    @Override
    public List<Order> findOrders(OrderSearch orderSearch) {
        return null;
    }

}

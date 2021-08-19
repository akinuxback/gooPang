package com.aki.goosinsa.service.order;

import com.aki.goosinsa.domain.entity.delivery.Delivery;
import com.aki.goosinsa.domain.entity.item.Item;
import com.aki.goosinsa.domain.entity.order.Order;
import com.aki.goosinsa.domain.entity.order.OrderSearch;
import com.aki.goosinsa.domain.entity.orderItem.OrderItem;
import com.aki.goosinsa.domain.entity.user.User;
import com.aki.goosinsa.repository.item.ItemRepository;
import com.aki.goosinsa.repository.order.OrderRepository;
import com.aki.goosinsa.repository.order.QDOrderRepository;
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

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     * */
    @Override
    @Transactional
    public Long order(Long userId, Long itemId, int count) {
        
        // 엔티티 조회
        User user = userRepository.findById(userId).get();
        Item item = (Item) itemRepository.findById(itemId).get();
        item.removeStock(count);

        // 배송정보 생성
        Delivery delivery = new Delivery();

        delivery.setAddress(user.getAddress());


        log.info("=========================================");
        log.info(user.getId());
        log.info(item.getId());
        log.info(item.getItemName());
        log.info(item.getStockQuantity());
        log.info(delivery.getAddress().getCity());
        log.info("=========================================");

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        log.info("=====================       orderItem        ==============================");
        log.info(orderItem.getTotalPrice());
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

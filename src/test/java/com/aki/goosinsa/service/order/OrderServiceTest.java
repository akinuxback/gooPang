package com.aki.goosinsa.service.order;

import com.aki.goosinsa.domain.entity.item.FoodItem;
import com.aki.goosinsa.domain.entity.order.Order;
import com.aki.goosinsa.domain.entity.order.OrderStatus;
import com.aki.goosinsa.domain.entity.user.User;
import com.aki.goosinsa.domain.entity.user.UserRole;
import com.aki.goosinsa.exception.NotEnoughStockException;
import com.aki.goosinsa.repository.item.ItemRepository;
import com.aki.goosinsa.repository.order.OrderRepository;
import com.aki.goosinsa.repository.user.UserRepository;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional
@Log4j2
class OrderServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private EntityManager em;

    private User user;

    private FoodItem item1;
    private FoodItem item2;
    private FoodItem item3;
    private FoodItem item4;
    private FoodItem item5;

    @BeforeEach
    public void beforeEach(){
        user = userRepository.findById(1L).get();
        item1 = (FoodItem) itemRepository.findById(1L).get();
        item2 = (FoodItem) itemRepository.findById(2L).get();
        item3 = (FoodItem) itemRepository.findById(3L).get();
        item4 = (FoodItem) itemRepository.findById(4L).get();
        item5 = (FoodItem) itemRepository.findById(5L).get();

    }

    @Test
    @Rollback(value = false)
    public void 상품주문() throws Exception{
        //given

        int orderCount = 2;

        //when
        Long orderId = orderService.order(user.getId(), item1.getId(), 2);
        log.info("=============================  orderId ==================================================");
        log.info(orderId);

        em.flush();
        em.clear();

        //then
        Order getOrder = orderRepository.findById(orderId).get();
        assertEquals(OrderStatus.ORDER, getOrder.getStatus());
        assertEquals(1, getOrder.getOrderItems().size());
//        assertEquals(16, item1.getStockQuantity());
        assertEquals(10000*orderCount, getOrder.getTotalPrice());
        log.info("======================= getTotalPrice ======================");
        log.info(getOrder.getTotalPrice());

    }

    /**
     *  주문한 수량이 재고수량을 초과시 , 예외가 발생 해야 한다.
     * */
    @Test
    public void 상품주문_재고수량초과() throws Exception{
        log.info(" item1.getStockQuantity()  -------------------->  " + item1.getStockQuantity());
        int orderCount = item1.getStockQuantity() + 1;  // 현재의 재고 수량보다 1 많게
        //when
        assertThrows(NotEnoughStockException.class, () -> {
            Long order = orderService.order(user.getId(), item1.getId(), orderCount);
        });

    }

    @Test
    @Rollback(value = false)
    public void 주문취소() throws Exception{
        //given
        int orderCount = 2;
        Long orderId = orderService.order(user.getId(), item1.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.getById(orderId);

        assertEquals("주문 취소시 상태는 cancel 이다", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문 취소된 상품은 그만큼 재고가 증가해야 한다.", 6, item1.getStockQuantity());

    }

}
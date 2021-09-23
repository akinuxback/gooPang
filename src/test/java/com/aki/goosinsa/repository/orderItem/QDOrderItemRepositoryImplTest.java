package com.aki.goosinsa.repository.orderItem;

import com.aki.goosinsa.domain.entity.orderItem.OrderItem;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Log4j2
class QDOrderItemRepositoryImplTest {

    @Autowired private OrderItemRepository orderItemRepository;
    @Autowired private QDOrderItemRepository qdOrderItemRepository;

    @BeforeEach
    public void before(){

    }

    @Test
    public void 구매내역_페이지() throws Exception{
        List<OrderItem> ol = qdOrderItemRepository.findListByIdAndStatusOrderJoinItemJoinUserJoinOrder(1L);
    }

    @Test
    public void 구매내역_페이지123() throws Exception{
        qdOrderItemRepository.findByIdAndStatusReadyJoinItemJoinUser(19L);
    }

}
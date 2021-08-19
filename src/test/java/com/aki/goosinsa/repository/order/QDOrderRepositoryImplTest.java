package com.aki.goosinsa.repository.order;

import com.aki.goosinsa.domain.entity.order.Order;
import com.aki.goosinsa.domain.entity.order.OrderSearch;
import com.aki.goosinsa.domain.entity.order.OrderStatus;
import com.aki.goosinsa.domain.entity.order.QOrder;
import com.aki.goosinsa.domain.entity.user.User;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.aki.goosinsa.domain.entity.order.QOrder.order;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional(readOnly = true)
@Log4j2
class QDOrderRepositoryImplTest {

    @Autowired
    QDOrderRepository qdOrderRepository;

    @Test
    public void searchTest() throws Exception{
        OrderSearch orderSearch1 = new OrderSearch();
        orderSearch1.setUsername("aki01");
        orderSearch1.setOrderStatus(OrderStatus.ORDER);

        OrderSearch orderSearch2 = new OrderSearch();
        orderSearch2.setUsername("aki01");
        orderSearch2.setOrderStatus(OrderStatus.CANCEL);

        OrderSearch orderSearch3 = new OrderSearch();
        orderSearch3.setUsername("");
        orderSearch3.setOrderStatus(OrderStatus.ORDER);

        OrderSearch orderSearch4 = new OrderSearch();
        orderSearch4.setUsername(null);
        orderSearch4.setOrderStatus(OrderStatus.ORDER);


        List<Order> result1 = qdOrderRepository.findAllOrderSearch(orderSearch1);
        List<Order> result2 = qdOrderRepository.findAllOrderSearch(orderSearch2);
        List<Order> result3 = qdOrderRepository.findAllOrderSearch(orderSearch3);
        List<Order> result4 = qdOrderRepository.findAllOrderSearch(orderSearch4);

        List<User> collect = result1.stream().map(r -> {
            return User.builder()
                    .name(r.getUser().getName())
                    .username(r.getUser().getUsername())
                    .build();
        }).collect(Collectors.toList());

        collect.forEach(c -> {
            assertThat(c).hasFieldOrPropertyWithValue("username", "aki01");
        });

        result2.forEach(r -> {
            assertThat(r).hasFieldOrPropertyWithValue("status", OrderStatus.CANCEL);
        });

//        assertThat(result3).extracting("username").containsExactly("aki01");
    }

}
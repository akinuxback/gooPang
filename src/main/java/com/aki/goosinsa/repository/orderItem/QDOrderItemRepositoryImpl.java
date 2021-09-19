package com.aki.goosinsa.repository.orderItem;

import com.aki.goosinsa.domain.entity.company.QCompany;
import com.aki.goosinsa.domain.entity.item.QItem;
import com.aki.goosinsa.domain.entity.order.Order;
import com.aki.goosinsa.domain.entity.order.OrderSearch;
import com.aki.goosinsa.domain.entity.order.OrderStatus;
import com.aki.goosinsa.domain.entity.orderItem.OrderItem;
import com.aki.goosinsa.domain.entity.orderItem.OrderItemStatus;
import com.aki.goosinsa.domain.entity.user.QUser;
import com.aki.goosinsa.repository.order.QDOrderRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.aki.goosinsa.domain.entity.company.QCompany.company;
import static com.aki.goosinsa.domain.entity.item.QItem.item;
import static com.aki.goosinsa.domain.entity.orderItem.QOrderItem.orderItem;
import static com.aki.goosinsa.domain.entity.user.QUser.user;

@Repository
public class QDOrderItemRepositoryImpl implements QDOrderItemRepository {

    private JPAQueryFactory queryFactory;

    public QDOrderItemRepositoryImpl(EntityManager em){
        queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<OrderItem> findListByIdAndStatusReadyJoinItemJoinUser(Long userId) {
        return queryFactory
                .select(orderItem)
                .from(orderItem)
                .join(orderItem.item)
                .join(orderItem.user)
                .where(orderItem.user.id.eq(userId).and(orderItem.status.eq(OrderItemStatus.READY)))
                .fetch();
    }

    @Override
    public List<OrderItem> findListByIdAndStatusOrderJoinItemJoinUser(Long userId) {
        return queryFactory
                .select(orderItem)
                .from(orderItem)
                .join(orderItem.item)
                .join(orderItem.user)
                .where(orderItem.user.id.eq(userId).and(orderItem.status.eq(OrderItemStatus.ORDER)))
                .fetch();
    }

    @Override
    public List<OrderItem> findListByIdAndStatusOrderJoinItemJoinUserJoinOrder(Long userId) {
        return queryFactory
                .select(orderItem)
                .from(orderItem)
                .join(orderItem.item).fetchJoin()
                .join(orderItem.user).fetchJoin()
                .leftJoin(orderItem.order).fetchJoin()
                .where(orderItem.user.id.eq(userId).and(orderItem.status.eq(OrderItemStatus.ORDER)))
                .fetch();
    }


}

package com.aki.goosinsa.repository.order;

import com.aki.goosinsa.domain.entity.order.Order;
import com.aki.goosinsa.domain.entity.order.OrderSearch;
import com.aki.goosinsa.domain.entity.order.OrderStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.aki.goosinsa.domain.entity.order.QOrder.order;

@Repository
@Transactional
public class QDOrderRepositoryImpl implements QDOrderRepository{

    private JPAQueryFactory queryFactory;

    public QDOrderRepositoryImpl(EntityManager em){
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Order> findAllOrderSearch(OrderSearch orderSearch) {

//        BooleanBuilder builder = new BooleanBuilder();
//        if(orderSearch.getUsername() != null){
//            builder.and(order.user.username.eq(orderSearch.getUsername()));
//        }
//
//        if(orderSearch.getOrderStatus() != null){
//            builder.and(order.status.eq(orderSearch.getOrderStatus()));
//        }

        List<Order> orderSearchList = queryFactory
                .select(order)
                .from(order)
                .join(order.user).fetchJoin()
//                .where(usernameEq(orderSearch.getUsername()), statusEq(orderSearch.getOrderStatus()))
                .where(usernameAndStatusEq(orderSearch))
                .fetch();

        return orderSearchList;
    }

//    private Predicate usernameEq(String username) {
//        return username != null ? order.user.username.eq(username) : null;
//    }
//
//    private Predicate statusEq(OrderStatus orderStatus) {
//        return orderStatus != null ? order.status.eq(orderStatus) : null;
//    }

    private BooleanExpression usernameEq(String username) {
        return order.user.username.eq(username);
    }

    private BooleanExpression statusEq(OrderStatus orderStatus) {
        return order.status.eq(orderStatus);
    }

    private BooleanExpression usernameAndStatusEq(OrderSearch orderSearch){
        if(orderSearch.getUsername() == null || orderSearch.getUsername() == ""){
            return statusEq(orderSearch.getOrderStatus());
        }

        if(orderSearch.getOrderStatus() == null){
            return usernameEq(orderSearch.getUsername());
        }

        return usernameEq(orderSearch.getUsername()).and(statusEq(orderSearch.getOrderStatus()));
    }

}

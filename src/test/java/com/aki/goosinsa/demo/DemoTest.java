package com.aki.goosinsa.demo;

import com.aki.goosinsa.domain.dto.item.FoodGroups;
import com.aki.goosinsa.domain.dto.item.ItemDto;
import com.aki.goosinsa.domain.entity.item.Item;
import com.aki.goosinsa.domain.entity.item.QFoodItem;
import com.aki.goosinsa.domain.entity.item.QItem;
import com.aki.goosinsa.domain.entity.item.QUploadFile;
import com.aki.goosinsa.domain.entity.user.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static com.aki.goosinsa.domain.entity.item.QFoodItem.foodItem;
import static com.aki.goosinsa.domain.entity.item.QItem.item;
import static com.aki.goosinsa.domain.entity.item.QUploadFile.uploadFile;
import static com.aki.goosinsa.domain.entity.user.QUser.user;

@SpringBootTest
@Transactional
public class DemoTest {

    @PersistenceContext
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before(){
        queryFactory = new JPAQueryFactory(em);

    }

    @Test
    public void test1(){

        List<Item> fetch = queryFactory.select(item)
                .from(item)
                .fetch();

        fetch.forEach(f -> System.out.println("f = " + f.getUploadFile().getServerFileName()));
    }

    @Test
    public void test2(){

        queryFactory.select(item)
                .from(item)
                .fetch();
    }

    @Test
    public void test3(){
        em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

}

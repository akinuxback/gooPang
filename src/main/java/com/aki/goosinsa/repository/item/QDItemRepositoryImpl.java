package com.aki.goosinsa.repository.item;

import com.aki.goosinsa.domain.dto.item.FoodItemDto;
import com.aki.goosinsa.domain.dto.item.ItemDto;
import com.aki.goosinsa.domain.dto.item.QItemDto;
import com.aki.goosinsa.domain.entity.item.*;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static com.aki.goosinsa.domain.entity.item.QFoodItem.foodItem;
import static com.aki.goosinsa.domain.entity.item.QItem.item;
import static com.aki.goosinsa.domain.entity.item.QUploadFile.uploadFile;

@Repository
public class QDItemRepositoryImpl implements QDItemRepository{

    private JPAQueryFactory queryFactory;

    public QDItemRepositoryImpl(EntityManager em){
        queryFactory = new JPAQueryFactory(em);
    }

    public Page<FoodItemDto> findAllPaging(Pageable pageable) {
        QueryResults<FoodItemDto> results = queryFactory
//                .select(Projections.constructor(ItemDto.class, item)) -> 추상 클래스로 만들려하면
                .select(Projections.constructor(FoodItemDto.class, foodItem))
                .from(foodItem)
                .join(foodItem.uploadFile)
                .fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(foodItem.id.desc())
                .fetchResults();

        List<FoodItemDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Slice<ItemDto> findAllSlice(Pageable pageable) {
        return null;
    }

}

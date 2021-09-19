package com.aki.goosinsa.repository.item;

import com.aki.goosinsa.controller.food.FoodSearch;
import com.aki.goosinsa.domain.dto.item.FoodGroups;
import com.aki.goosinsa.domain.dto.item.FoodItemDto;
import com.aki.goosinsa.domain.dto.item.ItemDto;
import com.aki.goosinsa.domain.entity.item.QItem;
import com.aki.goosinsa.domain.entity.user.QUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.aki.goosinsa.domain.entity.item.QFoodItem.foodItem;
import static com.aki.goosinsa.domain.entity.item.QItem.item;
import static com.aki.goosinsa.domain.entity.user.QUser.user;

@Repository
@Log4j2
public class QDItemRepositoryImpl implements QDItemRepository{

    private JPAQueryFactory queryFactory;

    public QDItemRepositoryImpl(EntityManager em){
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public FoodItemDto findByIdJoinUploadFile(Long id){
        return queryFactory
                .select(Projections.constructor(FoodItemDto.class, foodItem))
                .from(foodItem)
                .join(foodItem.uploadFile).fetchJoin()
                .where(foodItem.id.eq(id))
                .fetchOne();

    }

    @Override
    public FoodItemDto findByIdJoinUploadFileJoinCompany(Long id){
        return queryFactory
                .select(Projections.constructor(FoodItemDto.class, foodItem))
                .from(foodItem)
                .join(foodItem.uploadFile).fetchJoin()
                .join(foodItem.company).fetchJoin()
                .where(foodItem.id.eq(id))
                .fetchOne();

    }



    @Override
    public Page itemFindAll(FoodSearch foodSearch) {

        queryFactory
                .select(foodItem)
                .from(foodItem)
                .join(foodItem.company).fetchJoin()
                .join(foodItem.uploadFile).fetchJoin()
                .fetchResults();

        return null;
    }

    public Page<FoodItemDto> findAllPaging(Pageable pageable, FoodSearch foodSearch) {
        Long userId = foodSearch.getUserId();
        String companyNo = foodSearch.getCompanyNo();
        String companyName = foodSearch.getCompanyName();
        String foodName = foodSearch.getFoodName();
        int price = foodSearch.getPrice() == null ? 0 : foodSearch.getPrice();
        String foodGroups = foodSearch.getFoodGroups();
        String title = foodSearch.getFoodGroupsOfTitle();

        BooleanBuilder builder = new BooleanBuilder();
        if(userId != null){
            builder.and(foodItem.company.user.id.eq(userId));
        }

        if(StringUtils.hasText(companyNo)){
            builder.and(foodItem.company.companyNo.eq(companyNo.trim()));
        }

        if(StringUtils.hasText(companyName)){
            builder.and(foodItem.company.companyName.eq(companyName.trim()));
        }

        if(StringUtils.hasText(foodName)){
            builder.and(foodItem.itemName.eq(foodName.trim()));
        }

        if(price > 0){
            builder.and(foodItem.price.loe(price));
        }

        if(StringUtils.hasText(foodGroups)){
            builder.and(foodItem.foodGroups.eq(FoodGroups.valueOf(foodGroups)));
        }

        if(StringUtils.hasText(title)){
            builder.and(foodItem.foodGroupsOfTitle.eq(title));
        }

        QueryResults<FoodItemDto> results = queryFactory
//                .select(Projections.constructor(ItemDto.class, item)) -> abstract 로 만들려하면 오류남
                .select(Projections.constructor(FoodItemDto.class, foodItem))
                .from(foodItem)
                .where(builder)
                .join(foodItem.uploadFile).fetchJoin()
                .join(foodItem.company).fetchJoin()
                .join(foodItem.company.user)
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

package com.aki.goosinsa.repository.company;

import com.aki.goosinsa.domain.dto.company.CompanyDto;
import com.aki.goosinsa.domain.dto.company.CompanySearch;
import com.aki.goosinsa.domain.dto.item.FoodGroups;
import com.aki.goosinsa.domain.dto.item.FoodItemDto;
import com.aki.goosinsa.domain.entity.company.Company;
import com.aki.goosinsa.domain.entity.item.FoodItem;
import com.aki.goosinsa.domain.entity.item.QUploadFile;
import com.aki.goosinsa.domain.entity.user.QUser;
import com.aki.goosinsa.util.RepositoryHelper;
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

import static com.aki.goosinsa.domain.entity.company.QCompany.company;
import static com.aki.goosinsa.domain.entity.item.QFoodItem.foodItem;
import static com.aki.goosinsa.domain.entity.item.QUploadFile.uploadFile;
import static com.aki.goosinsa.domain.entity.user.QUser.user;

@Repository
@Log4j2
public class QDCompanyRepositoryImpl implements QDCompanyRepository{

    private JPAQueryFactory queryFactory;

    public QDCompanyRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<CompanyDto> findAllPaging(Pageable pageable, CompanySearch companySearch) {
        log.info("===========================================");
        log.info(pageable.getOffset());
        log.info(pageable.getPageSize());
        log.info(pageable.getPageNumber());
        log.info(pageable.toString());

        String foodGroups = companySearch.getFoodGroups();
        try {
            FoodGroups.valueOf(foodGroups);
        } catch (Exception e){
            foodGroups = "";
        }

        String companyNo = companySearch.getCompanyNo();
        String companyName = companySearch.getCompanyName();
        String username = companySearch.getUsername();
        String name = companySearch.getName();

        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(foodGroups)){
            builder.and(company.foodGroups.eq(FoodGroups.valueOf(foodGroups)));
        }

        if(StringUtils.hasText(companyNo)){
            builder.and(company.companyNo.eq(companyNo.trim()));
        }

        if(StringUtils.hasText(companyName)){
            builder.and(company.companyName.eq(companyName.trim()));
        }

        try {
            if(StringUtils.hasText(username)){
                builder.and(company.user.username.eq(username.trim()));
            }
        } catch (Exception e){
            e.printStackTrace();
        }


        if(StringUtils.hasText(name)){
            builder.and(company.user.name.eq(name.trim()));
        }

        QueryResults<CompanyDto> results = queryFactory
                .select(Projections.constructor(CompanyDto.class, company))
//                .select(company)
                .from(company)
                .where(builder)
                .innerJoin(company.user, user).fetchJoin()
                .innerJoin(company.uploadFile, uploadFile).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(company.createDate.desc())
                .fetchResults();

        List<CompanyDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Slice<CompanyDto> findAllPagingSlice(Pageable pageable, CompanySearch companySearch) {
        log.info("pageable.toString() =====>" + pageable.toString());

        String foodGroups = companySearch.getFoodGroups();
//        log.info("===================================================================================");
//        log.info("===================================================================================");
//        log.info("===================================================================================");
//        log.info("===================================================================================");
//        log.info("===================================================================================");
//        log.info(foodGroups);
//        if(companySearch.getFoodGroups() != "All"){
//            foodGroups = companySearch.getFoodGroups();
//        }

        String companyNo = companySearch.getCompanyNo();
        String companyName = companySearch.getCompanyName();
        String username = companySearch.getUsername();
        String name = companySearch.getName();

        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(foodGroups)){
            builder.and(company.foodGroups.eq(FoodGroups.valueOf(foodGroups)));
        }

        if(StringUtils.hasText(companyNo)){
            builder.and(company.companyNo.eq(companyNo.trim()));
        }

        if(StringUtils.hasText(companyName)){
            builder.and(company.companyName.eq(companyName.trim()));
        }

        try {
            if(StringUtils.hasText(username)){
                builder.and(company.user.username.eq(username.trim()));
            }
        } catch (Exception e){
            e.printStackTrace();
        }


        if(StringUtils.hasText(name)){
            builder.and(company.user.name.eq(name.trim()));
        }

        QueryResults<CompanyDto> results = queryFactory
                .select(Projections.constructor(CompanyDto.class, company))
//                .select(company)
                .from(company)
                .where(builder)
                .innerJoin(company.user, user).fetchJoin()
                .innerJoin(company.uploadFile, uploadFile).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(company.companyNo.desc())
                .fetchResults();

        List<CompanyDto> content = results.getResults();
        long total = results.getTotal();

        return RepositoryHelper.toSlice(content, pageable);
    }

    @Override
    public Page<FoodItem> companyFindFoodItemList(String companyNo, CompanySearch companySearch){
        QueryResults<FoodItem> results = queryFactory
                .select(foodItem)
                .from(foodItem)
                .where(foodItem.company.companyNo.eq(companyNo))
                .leftJoin(foodItem.company).fetchJoin()
                .innerJoin(foodItem.uploadFile).fetchJoin()
                .offset(companySearch.getPageable().getOffset())
                .limit(companySearch.getPageable().getPageSize())
                .fetchResults();

        List<FoodItem> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, companySearch.getPageable(), total);
    };

    @Override
    public List<Company> companyOfUserAndUploadFileJoin(Long userId){
        return queryFactory
                .select(company)
                .from(company)
                .join(company.user).fetchJoin()
                .join(company.uploadFile).fetchJoin()
                .where(company.user.id.eq(userId))
                .orderBy(company.createDate.desc())
                .fetch();
    }

    @Override
    public CompanyDto companyJoinUserAndUploadFileFindByCompanyNo(String companyNo) {
        CompanyDto companyDto = queryFactory
                .select(Projections.constructor(CompanyDto.class, company))
                .from(company)
                .join(company.user).fetchJoin()
                .join(company.uploadFile)
                .where(company.companyNo.eq(companyNo))
                .fetchOne();

        return companyDto;
    }

    //왜 있는거지 이거 테스트용인가?
    public List<FoodItem> joinCompany(){
        return queryFactory
                .select(foodItem)
                .from(foodItem)
                .join(foodItem.company).fetchJoin()
                .fetch();
    }

    

}

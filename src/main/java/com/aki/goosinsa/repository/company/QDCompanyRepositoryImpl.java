package com.aki.goosinsa.repository.company;

import com.aki.goosinsa.domain.dto.company.CompanyDto;
import com.aki.goosinsa.domain.dto.company.CompanySearch;
import com.aki.goosinsa.domain.entity.company.Company;
import com.aki.goosinsa.domain.entity.item.FoodItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static com.aki.goosinsa.domain.entity.company.QCompany.company;
import static com.aki.goosinsa.domain.entity.item.QFoodItem.foodItem;

@Repository
@Log4j2
public class QDCompanyRepositoryImpl implements QDCompanyRepository{

    private JPAQueryFactory queryFactory;

    public QDCompanyRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Company> findAllPaging(Pageable pageable, CompanySearch companySearch) {
        log.info("===========================================");
        log.info(pageable.getOffset());
        log.info(pageable.getPageSize());
        log.info(pageable.getPageNumber());

        String companyNo = companySearch.getCompanyNo();
        String companyName = companySearch.getCompanyName();
        String username = companySearch.getUsername();
        String name = companySearch.getName();

        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(companyNo)){
            builder.and(company.companyNo.eq(companyNo.trim()));
        }

        if(StringUtils.hasText(companyName)){
            builder.and(company.companyName.eq(companyName.trim()));
        }

        if(StringUtils.hasText(username)){
            builder.and(company.user.username.eq(username.trim()));
        }

        if(StringUtils.hasText(name)){
            builder.and(company.user.name.eq(name.trim()));
        }

        QueryResults<Company> results = queryFactory
                .select(company)
                .from(company)
                .where(builder)
                .join(company.user).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(company.companyNo.desc())
                .fetchResults();

        List<Company> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
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

package com.aki.goosinsa.repository.company;

import com.aki.goosinsa.domain.dto.company.CompanyDto;
import com.aki.goosinsa.domain.dto.company.CompanySearch;
import com.aki.goosinsa.domain.entity.company.Company;
import com.aki.goosinsa.domain.entity.company.QCompany;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static com.aki.goosinsa.domain.entity.company.QCompany.company;

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
                .join(company.user)
                .fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(company.companyNo.desc())
                .fetchResults();

        List<Company> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

}

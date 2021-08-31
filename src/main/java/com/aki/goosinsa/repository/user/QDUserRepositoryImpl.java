package com.aki.goosinsa.repository.user;

import com.aki.goosinsa.controller.user.UserSearch;
import com.aki.goosinsa.domain.entity.company.QCompany;
import com.aki.goosinsa.domain.entity.user.QUser;
import com.aki.goosinsa.domain.entity.user.User;
import com.aki.goosinsa.domain.entity.user.UserDto;
import com.aki.goosinsa.domain.entity.user.UserRole;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
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
import static com.aki.goosinsa.domain.entity.user.QUser.user;

@Repository
@Log4j2
public class QDUserRepositoryImpl implements QDUserRepository{

    private JPAQueryFactory queryFactory;

    public QDUserRepositoryImpl(EntityManager em){
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<User> findUsers(){
        List<User> userList = queryFactory
                .selectFrom(user)
                .fetch();
        return userList;
    }

    @Override
    public User userLeftJoinCompanyFindById(Long id) {
        return queryFactory
                .select(user)
                .from(user)
                .leftJoin(user.companyList).fetchJoin()
                .where(user.id.eq(id))
                .fetchOne();
    }

    @Override
    public User findById(Long id) {
        return queryFactory.selectFrom(user).where(user.id.eq(id)).fetchOne();
    }

    @Override
    public User findByUsername(String username) {
        User user = queryFactory.selectFrom(QUser.user).where(QUser.user.username.eq(username)).fetchOne();
        return user;
    }

    @Override
    public Page<UserDto> usersPaging(Pageable pageable, UserSearch userSearch) {
        String Susername = userSearch.getUsername();
        String Sname = userSearch.getName();
        String SphoneNumber = userSearch.getPhoneNumber();
        String userRole = userSearch.getUserRole();

        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(Susername)){
            builder.and(user.username.eq(Susername));
        }

        if(StringUtils.hasText(Sname)){
            builder.and(user.name.eq(Sname));
        }

        if(StringUtils.hasText(SphoneNumber)){
            builder.and(user.phoneNumber.eq(SphoneNumber));
        }

        if(StringUtils.hasText(userRole)){
            builder.and(user.role.eq(UserRole.valueOf(userRole)));
        }



        QueryResults<UserDto> results = queryFactory
                .select(Projections.constructor(UserDto.class, user))
                .from(user)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(user.id.desc())
                .fetchResults();

        List<UserDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);

    }

}

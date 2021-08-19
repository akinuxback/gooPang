package com.aki.goosinsa.repository.user;

import com.aki.goosinsa.domain.entity.user.QUser;
import com.aki.goosinsa.domain.entity.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.aki.goosinsa.domain.entity.user.QUser.user;

@Repository
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
    public User findById(Long id) {
        return queryFactory.selectFrom(user).where(user.id.eq(id)).fetchOne();
    }

    @Override
    public User findByUsername(String username) {
        User user = queryFactory.selectFrom(QUser.user).where(QUser.user.username.eq(username)).fetchOne();
        return user;
    }

}

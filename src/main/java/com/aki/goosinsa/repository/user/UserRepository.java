package com.aki.goosinsa.repository.user;

import com.aki.goosinsa.domain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// @Repository 어노테이션이 없어도 ioc 된다. 이유는 JpaRepository 상속했기 때문...
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByPhoneNumber(String phoneNumber);

}

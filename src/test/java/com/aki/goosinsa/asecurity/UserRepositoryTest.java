package com.aki.goosinsa.asecurity;

import com.aki.goosinsa.domain.entity.user.User;
import com.aki.goosinsa.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testUser() throws Exception{

        Optional<User> findId = userRepository.findById(1L);

    }

}
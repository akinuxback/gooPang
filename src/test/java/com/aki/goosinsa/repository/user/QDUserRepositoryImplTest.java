package com.aki.goosinsa.repository.user;

import com.aki.goosinsa.controller.user.UserSearch;
import com.aki.goosinsa.domain.domain.Address;
import com.aki.goosinsa.domain.dto.item.FoodItemDto;
import com.aki.goosinsa.domain.entity.item.FoodItem;
import com.aki.goosinsa.domain.entity.user.User;
import com.aki.goosinsa.domain.entity.user.UserDto;
import com.aki.goosinsa.domain.entity.user.UserRole;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
@Transactional
@Log4j2
class QDUserRepositoryImplTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    QDUserRepository qdUserRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    public void before(){
        Address address = new Address("서울시 송파구 방이동", "1701호", "123-123");
        IntStream.rangeClosed(1, 100).forEach( i -> {
            User user = User.builder()
                    .username("username333" + i)
                    .name("name1")
                    .password("1234")
                    .email("akinux100" + i + "@gmail.com")
                    .role(UserRole.ROLE_ADMIN)
                    .phoneNumber("010111111" + i)
                    .address(address)
                    .build();
            userRepository.save(user);
        });

        em.flush();
        em.clear();

    }

    @Test
    public void findUsers() throws Exception{
        List<User> users = qdUserRepository.findUsers();
        List<UserDto> userDtoList = users.stream()
                .map(u -> UserDto.toDto(u))
                .collect(Collectors.toList());

    }

    @Test
    public void findById() {
        qdUserRepository.findById(1L);
    }

    @Test
    public void findByUsername() throws Exception{
        User username1 = qdUserRepository.findByUsername("username1");
        log.info("return =====> " + username1.getUsername());
    }

    @Test
    @Rollback(value = false)
    public void usersPaging(){
        UserSearch userSearch = new UserSearch();
        userSearch.setUsername("username1");
        userSearch.setName("name4");
        PageRequest pageable = PageRequest.of(0, 10);
        Page<UserDto> pages = qdUserRepository.usersPaging(pageable, userSearch);
        List<UserDto> content = pages.getContent();
        content.forEach(c -> {
            log.info(" c.getUsername() ----> " + c.getUsername());
            log.info(" c.getId() ----> " + c.getId());
        });
    }

    @Test
    public void userList(){
        List<User> all = userRepository.findAll();
        all.forEach(u -> log.info("user All " + u.getUsername()));
    }
}
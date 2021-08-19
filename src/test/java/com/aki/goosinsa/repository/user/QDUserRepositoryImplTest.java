package com.aki.goosinsa.repository.user;

import com.aki.goosinsa.domain.dto.item.FoodItemDto;
import com.aki.goosinsa.domain.entity.item.FoodItem;
import com.aki.goosinsa.domain.entity.user.User;
import com.aki.goosinsa.domain.entity.user.UserDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional
@Log4j2
class QDUserRepositoryImplTest {

    @Autowired
    QDUserRepository qdUserRepository;

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
        qdUserRepository.findByUsername("admin");
    }
}
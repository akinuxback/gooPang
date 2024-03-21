package com.aki.goosinsa.repository.company;

import com.aki.goosinsa.domain.domain.AddressDto;
import com.aki.goosinsa.domain.entity.company.Company;
import com.aki.goosinsa.domain.entity.user.User;
import com.aki.goosinsa.domain.entity.user.UserDto;
import com.aki.goosinsa.domain.entity.user.UserRole;
import com.aki.goosinsa.repository.user.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Log4j2
class CompanyRepositoryTest {
    @Autowired private EntityManager em;
    @Autowired private UserRepository userRepository;
    @Autowired private CompanyRepository companyRepository;
    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;


    @BeforeEach
    public void before(){

        IntStream.rangeClosed(1, 100).forEach(i -> {
            UserDto userDto = UserDto.builder()
                    .role(UserRole.ROLE_MANAGER)
                    .username("user" + i)
                    .name("박보영")
                    .password("1111")
                    .email("google@gmail.com")
                    .phoneNumber("010-0000-1111")
                    .addressDto(new AddressDto("ㅁㄴㅇㅁㄴㅇㅁㄴㅇ"))
                    .build();

            User user = User.toEntity(userDto, bCryptPasswordEncoder);
            userRepository.save(user);
        });

        User findUser = userRepository.findByUsername("user1");

        Company company = Company.builder()
                .companyNo("111-1111-111")
                .companyName("qweqweqwe")
                .abbr("qwe")
                .user(findUser)
                .build();

        companyRepository.save(company);

        em.flush();
        em.clear();
    }

    @Test
    @Rollback(value = false)
    public void crudTest() throws Exception{


        Company findCompany = companyRepository.findById("111-1111-111").orElseThrow();
        log.info("=====================================");
        log.info(findCompany.getCompanyNo());

    }
    
}

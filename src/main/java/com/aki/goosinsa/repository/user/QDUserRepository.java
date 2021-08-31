package com.aki.goosinsa.repository.user;

import com.aki.goosinsa.controller.user.UserSearch;
import com.aki.goosinsa.domain.entity.user.User;
import com.aki.goosinsa.domain.entity.user.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QDUserRepository {

    public List<User> findUsers();

    public User userLeftJoinCompanyFindById(Long id);

    public User findById(Long id);

    public User findByUsername(String username);

    public Page<UserDto> usersPaging(Pageable pageable, UserSearch userSearch);
}

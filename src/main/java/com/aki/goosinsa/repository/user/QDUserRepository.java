package com.aki.goosinsa.repository.user;

import com.aki.goosinsa.domain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QDUserRepository {

    public List<User> findUsers();

    public User findById(Long id);

    public User findByUsername(String username);
}

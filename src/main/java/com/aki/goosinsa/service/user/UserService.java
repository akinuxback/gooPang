package com.aki.goosinsa.service.user;

import com.aki.goosinsa.domain.entity.user.User;
import com.aki.goosinsa.repository.user.QDUserRepository;
import com.aki.goosinsa.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final QDUserRepository qdUserRepository;

    public List<User> findUsers(){
        return qdUserRepository.findUsers();
    }

    public User findById(Long id){
        return qdUserRepository.findById(id);
    }

    public User findByUsername(String username){
        return qdUserRepository.findByUsername(username);
    }

}

package com.aki.goosinsa.asecurity.auth;

import com.aki.goosinsa.repository.user.UserRepository;
import com.aki.goosinsa.domain.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 시큐리티 설정에서 loginProcessingUrl("/login") 으로 걸어 놨기 때문에
 * login 요청이 오면 자동으로 UserDetailsService 타입으로 ioc 되어 있는 loadUserByUsername 함수가 실행
 */

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    //session(내부 Authentication(내부 UserDetails) <-> 시큐리티의 session 에 들어가게됨
    @Override                              // view 에서 설정한 name = username 으로 넘어온 파라티터를 찾는다
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username);
        if(userEntity != null){
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}

package com.aki.goosinsa.asecurity.auth;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인을 진행이 완료가 되면, 시큐리티 자신만의 session 을 만들어 줍니다. (Security ContextHolder)
// 여기 들어갈수 있는 session 정보는 오브젝트가 정해져 있다.
// 오브젝트 => Authentication 타입 객체
// Authentication 안에 User 정보가 있어야 됨.
// User 오브젝트 타입 => UserDetails 타입 객체
// Security Session => Authentication => UserDetails(PrincipalDetails)

import com.aki.goosinsa.domain.entity.user.User;
import com.aki.goosinsa.domain.entity.user.UserDto;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user;
    private Map<String, Object> attributes;
    
    //일반 로그인 생성자
    public PrincipalDetails(User user) {
        this.user = user;
        this.attributes = attributes;
    }

    //OAuth 로그인 생성자
    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    // OAuth2User

    public Long getId(){
        return user.getId();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // OAuth2User

    @Override
    public String getName() {
        return user.getName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
         Collection<GrantedAuthority> collect = new ArrayList<>();
         collect.add(new GrantedAuthority() {
             @Override
             public String getAuthority() {
                 return String.valueOf(user.getRole());
             }
         });
         return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
    // 이 계정 만료 됬니???

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    // 이 계정 잠겼니 ???

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    // 이 계정 기간이 지났니??? 1년? 비밀번호를 너무 오래 사용했니???

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    // 이 계정이 활성화 되어 있니???

    @Override
    public boolean isEnabled() {
        // [1] 우리 사이트!! 1년동안 회원이 로그인을 안했다면???
        //          -> 현재 시간 -(빼기) 로긴데이트, = 1년이 지나면, false 이런식으로 만들면 된다.
        return true;
    }
}

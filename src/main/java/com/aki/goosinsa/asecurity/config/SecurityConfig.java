package com.aki.goosinsa.asecurity.config;

import com.aki.goosinsa.asecurity.oauth.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *  구글 로그인이 완료된 뒤의 후처리가 필요함. 
 *  1.코드받기(인증), 2.액세스 토큰(권한), 3.사용자프로필 정보를 가져와서 
 *  4-1.그 정보를 토대로 회원가입을 자동으로 진행시키기도 함
 *  4-2 (이메일, 전화번호, 이름, 아이디) 예) 쇼핑몰 -> (집주소), 예) 백화점몰 -> (VIP 등급, 일반등급)
 * */

@Configuration
@EnableWebSecurity //활성화를 해준다. // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성화, preAuthorize - postAuthorize 두개의 어노테이션 활성화
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalOauth2UserService principalOauth2UserService;

    // 해당 메서드의 리턴되는 ioc 를 리턴 해준다.
    @Bean
    public static BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() // 인증만 되면 들어갈 수 있는 주소
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
//                .antMatchers("/admin/**/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/admin/**/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/loginForm") // antMatchers 로 접속시 loginForm 을 보여준다.
                 // .usernameParameter("username2")
                .loginProcessingUrl("/login") // view 의 form action 에 지정한 /login 주소가 호출이 되면 -> 시큐리티가 낚아채서 대신 로그인을 진행해 줍니다.
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/loginForm")
                .userInfoEndpoint()
                .userService(principalOauth2UserService); // 구글 로그인이 완료된 뒤의 후처리가 필요함. Tip. 코드 X (엑세스토큰 _ 사용자 프로필정보 O)

    }



}

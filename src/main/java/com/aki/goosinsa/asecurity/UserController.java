package com.aki.goosinsa.asecurity;

import com.aki.goosinsa.asecurity.auth.PrincipalDetails;
import com.aki.goosinsa.domain.entity.user.User;
import com.aki.goosinsa.domain.entity.user.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/loginForm")
    public String loginForm(){
        return "user/loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "user/joinForm";
    }

    @PostMapping("/join")
    public String join(UserDto userDto){
        log.info("user = {}", userDto);
        User user = User.toEntity(userDto, bCryptPasswordEncoder);
        log.info("user entity = {}", user);

        userRepository.save(user);
        return "redirect:/loginForm";
    }






    /**
     *  ==================================================  아래 부터는 학습용 ================================================================================
     * */


    @GetMapping("/test/login")
    public @ResponseBody String testLogin(Authentication auth, @AuthenticationPrincipal PrincipalDetails userDetails){ //DI(의존성 주입)
        System.out.println("/test/login ====================");
        PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal();
        System.out.println("authentication : " + principalDetails.getUser());

        System.out.println("userDetails : " + userDetails.getUser());
        return "세션 정보 확인하기";
    }

    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOauthLogin(Authentication auth, @AuthenticationPrincipal OAuth2User oAuth){ //DI(의존성 주입)
        System.out.println("/test/oAuth/login ====================");
        OAuth2User oAuth2User = (OAuth2User) auth.getPrincipal();
        System.out.println("oAuth2User.getAttributes() : " + oAuth2User.getAttributes());
        System.out.println("oAuth2User.getAttributes().get(\"username\") : " + oAuth2User.getAttributes().get("name"));
        System.out.println("oAuth2User : " + oAuth.getAttributes());
        return "Oauth 세션 정보 확인하기";
    }

    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println("principalDetails : " + principalDetails.getUser());
        return "user";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "개인정보";
    }

    // 메서드 수행 이전, 두개 이상 걸고 싶을때 PreAuthorize
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/data")
    public @ResponseBody String data(){
        return "데이터";
    }

    //잘 안씀
    // 메서드 수행 이후, 두개 이상 걸고 싶을때 PreAuthorize
    @PostAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/post")
    public @ResponseBody String postAuth(){
        return "포스트";
    }

}

package com.aki.goosinsa.asecurity.oauth;

import com.aki.goosinsa.repository.user.UserRepository;
import com.aki.goosinsa.asecurity.auth.PrincipalDetails;
import com.aki.goosinsa.asecurity.oauth.provider.*;
import com.aki.goosinsa.domain.entity.user.User;
import com.aki.goosinsa.domain.entity.user.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    // 구글로 부터 받은 userRequest 데이터에 대한 후처리 되는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("getClientRegistration :  ==>  {}", userRequest.getClientRegistration()); //registration 으로 어떤 OAuth 로 로그인 했는지 확인 가능
        log.info("getAccessToken .getTokenValue():  ==>  {}", userRequest.getAccessToken().getTokenValue());



        OAuth2User oAuth2User = super.loadUser(userRequest);
        // 구글 로그인 버튼 클릭 -> 구글 로그인창 -> 로그인을 완료 -> code 를 리턴(OAuth-Client 라이브러리 -> AccessToken 요청)
        // userRequest 정보 -> loadUser 함수 호출 -> 구글로부터 회원프로필 받아준다.
        log.info("oAuth2User.getAttributes() ==> : {}", oAuth2User.getAttributes()); // 이 정보를 토대로 강제로 회원가입 진행

        // 회원가입을 강제로 진행해볼 예정
        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            log.info("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("facebook")){
            log.info("페이스북 로그인 요청");
            oAuth2UserInfo = new FacebookUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            log.info("네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")){
            log.info("카카오 로그인 요청");
            oAuth2UserInfo = new KakaoUserInfo((Map)oAuth2User.getAttributes().get("properties"));
        } else {
            log.info("우리는 구글과 페이스북과 네이버만 지원해요 ㅎㅎㅎ");
        }


        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider + "_" + providerId; // 예 google_100224515648711555 <-> 이렇게 하면 username 이 다른 oAuth 와 충돌날일이 없다.
        String password = bCryptPasswordEncoder.encode("겟인데어");
        String email = oAuth2UserInfo.getEmail();
        UserRole role = UserRole.ROLE_USER;

        User userEntity = userRepository.findByUsername(username);

        if(userEntity == null){
            log.info("OAuth 로그인이 최초입니다.");
             userEntity = User.builder()
                    .username(username)
                     .password(password)
                     .email(email)
                     .role(role)
                     .provider(provider)
                     .providerId(providerId)
                     .build();
             userRepository.save(userEntity);
        } else {
            log.info("로그인을 이미 한적이 있습니다. 당신은 자동회원가입이 되어 있습니다.");
        }

        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());

    }
}

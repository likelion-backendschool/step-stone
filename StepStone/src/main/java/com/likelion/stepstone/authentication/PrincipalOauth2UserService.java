package com.likelion.stepstone.authentication;


import com.likelion.stepstone.authentication.provider.GoogleUserInfo;
import com.likelion.stepstone.authentication.provider.NaverUserInfo;
import com.likelion.stepstone.authentication.provider.OAuth2UserInfo;
import com.likelion.stepstone.user.UserRepository;
import com.likelion.stepstone.user.model.UserEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final UserRepository userRepository;

  PrincipalOauth2UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  // 구글로 부터 받은 userRequest 데이터에 대한 후처리되는 함수
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//    System.out.println("getClientRegistration : " + userRequest.getClientRegistration());
//    System.out.println("getAccessToken : " + userRequest.getAccessToken());

    OAuth2User oAuth2User = super.loadUser(userRequest);
//    System.out.println("getAttributes : " + oAuth2User.getAttributes());

    OAuth2UserInfo oAuth2UserInfo = null;
    if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
//      System.out.println("구글 로그인 요청");
      oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
    } else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
//      System.out.println("네이버 로그인 요청");
      oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
    } else {
      System.out.println("현재 구글과 네이버만 지원 중입니다.");
      return null;
    }

    String userId = oAuth2UserInfo.getUserId();
    String password = bCryptPasswordEncoder.encode("StepStone"); // 의미X
    String role = "ROLE_USER";
    String name = oAuth2UserInfo.getName();

    UserEntity userEntity = userRepository.findByUserId(userId).orElseGet(() ->
            userRepository.save(UserEntity.builder()
            .userId(userId)
            .password(password)
            .name(name)
            .role(role)
            .build()));

    return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
  }


}

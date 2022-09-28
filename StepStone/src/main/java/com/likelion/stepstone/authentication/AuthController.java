package com.likelion.stepstone.authentication;

import com.likelion.stepstone.authentication.jwt.JwtProperties;
import com.likelion.stepstone.authentication.jwt.JwtTokenProvider;
import com.likelion.stepstone.user.UserService;
import com.likelion.stepstone.user.model.JoinVo;
import com.likelion.stepstone.user.model.UserEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.likelion.stepstone.authentication.CookieUtils.*;


@Controller
public class AuthController {

  private final UserService userService;

  AuthController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("join")
  public String join(JoinVo joinVo) {
    return "user/joinForm";
  }

  @PostMapping("join")
  public String join(@Valid JoinVo joinVo, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "user/joinForm";
    }
    try {
      userService.join(joinVo);
    } catch (DataIntegrityViolationException e) {
      e.printStackTrace();
      bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
      return "user/joinForm";
    }catch(Exception e) {
      e.printStackTrace();
      bindingResult.reject("signupFailed", e.getMessage());
      return "user/joinForm";
    }

    return "user/loginForm";
  }

  @GetMapping("api/v1/user")
  public @ResponseBody Object user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
    return principalDetails;
  }
  @GetMapping("api/v1/admin")
  @ResponseBody
  public String admin() {
    return "admin";
  }

  @GetMapping("/oauth/login")
  public String oauthLogin(HttpServletResponse response, @AuthenticationPrincipal PrincipalDetails principalDetails) {
    UserEntity userEntity = principalDetails.getUser();

    String jwtToken = JwtTokenProvider.provide(principalDetails);
    addStrictCookie(response, JwtProperties.HEADER_STRING, jwtToken, JwtProperties.EXPIRATION_TIME / 1000);
    if(!userEntity.isLoginBefore()) {
      return "user/oauthJoinForm";
    }
    return "trick";
  }

  @PostMapping("/oauth/login")
  public String oauthLogin(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestParam String role) {
    UserEntity userEntity = principalDetails.getUser();
    userEntity.setRole(role);
    userEntity.setLoginBefore(true);
    userService.save(userEntity);
    return "redirect:/";
  }

  @GetMapping("/login")
  public String login() {
    return "user/loginForm";
  }
}
package com.likelion.stepstone.authentication;

import com.likelion.stepstone.user.UserService;
import com.likelion.stepstone.user.model.LoginVo;
import com.likelion.stepstone.user.model.UserEntity;
import com.likelion.stepstone.user.model.UserVo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;


@Controller
public class AuthController {

  private final UserService userService;

  AuthController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("join")
  public String join() {
    return "user/joinForm";
  }

  @PostMapping("join")
  public String join(LoginVo loginVo) {
    userService.join(loginVo);
    return "user/loginForm";
  }

  // user, manager, admin 권한만 접근 가능
  @GetMapping("api/v1/user")
  public @ResponseBody Object user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
    return principalDetails;
  }

  // manager, admin 권한만 접근 가능
  @GetMapping("api/v1/manager")
  public @ResponseBody String manager() {
    return "manager";
  }

  @GetMapping("api/v1/admin")
  @ResponseBody
  public String admin() {
    return "admin";
  }

  @GetMapping("/oauth/login")
  public String oauthLogin(@AuthenticationPrincipal PrincipalDetails principalDetails) {
    UserEntity userEntity = principalDetails.getUser();

    if(!userEntity.isLoginBefore()) {
      return "user/oauthJoinForm";
    }
    return "redirect:/";
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

  @GetMapping("/login-error")
  public String loginError(Model model) {
    model.addAttribute("loginError", true);
    return "user/loginForm";
  }
}
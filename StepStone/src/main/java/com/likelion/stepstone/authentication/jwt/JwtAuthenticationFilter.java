package com.likelion.stepstone.authentication.jwt;

import com.likelion.stepstone.authentication.PrincipalDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.likelion.stepstone.authentication.CookieUtils.addCookie;

// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 가 있음.
// /login 요청해서 username, password 전송하면 (post)
// UsernamePasswordAuthenticationFilter 동작을 함.
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private final AuthenticationManager authenticationManager;

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    System.out.println("JwtAuthenticationFilter : 로그인 시도중");

      System.out.println(request.getParameter("userId") + " " + request.getParameter("password"));

      UsernamePasswordAuthenticationToken authenticationToken =
              new UsernamePasswordAuthenticationToken(request.getParameter("userId"), request.getParameter("password"));

      // 2. 정상인지 로그인 시도를 해보는 것. authenticationManager로 로그인 시도를 하면!
      // PrincipalDetailsService의 loadUserByUsername() 함수가 실행됨.

      // 3. PrincipalDetails를 세션에 담고 (권한 관리를 위함)

      // 4. JWT 토큰을 만들어서 응답해줌.
      Authentication authentication =
              authenticationManager.authenticate(authenticationToken);

      // authenticaion 객체가 session 영역에 저장됨. => 로그인이 되었다는 뜻
      PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();

      System.out.println("로그인 완료됨: " + principalDetails.getUser().getUserId());

      return authentication;

  }

  // attemptAuthentication 함수가 정상적으로 실행되면 실행되는 함수
  // JWT 토큰을 만들어서 request 요청한 사용자에게 JWT 토큰을 response
  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
    System.out.println("인증이 완료됨");
    PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

    String jwtToken = JwtTokenProvider.provide(principalDetails);

    addCookie(response, JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken, JwtProperties.EXPIRATION_TIME);
    response.sendRedirect("/");
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
    response.sendRedirect("/login-error");
  }
}

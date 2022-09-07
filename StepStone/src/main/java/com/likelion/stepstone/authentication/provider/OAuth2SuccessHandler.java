package com.likelion.stepstone.authentication.provider;

import com.likelion.stepstone.authentication.PrincipalDetails;
import com.likelion.stepstone.authentication.jwt.JwtProperties;
import com.likelion.stepstone.authentication.jwt.JwtTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.likelion.stepstone.authentication.CookieUtils.*;
import static com.likelion.stepstone.authentication.provider.HttpCookieOAuth2AuthorizationRequestRepository.OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME;
import static com.likelion.stepstone.authentication.provider.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    System.out.println("OAuth2 인증이 완료됨");
    PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

    String jwtToken = JwtTokenProvider.provide(principalDetails);

    deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
    deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
    addCookie(response, JwtProperties.HEADER_STRING, jwtToken, JwtProperties.EXPIRATION_TIME);

    response.sendRedirect("/oauth/login");
  }
}
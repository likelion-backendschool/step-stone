package com.likelion.stepstone.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("authenticationProvider")
public class JwtAuthenticationProvider implements AuthenticationProvider {

  private final PasswordEncoder passwordEncoder;
  private final PrincipalDetailsService principalDetailsService;

  JwtAuthenticationProvider(PasswordEncoder passwordEncoder, PrincipalDetailsService principalDetailsService) {
    this.passwordEncoder = passwordEncoder;
    this.principalDetailsService = principalDetailsService;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String loginId = authentication.getName();

    String password = (String) authentication.getCredentials();

    System.out.println("로그인 시도중인 아이디 : " + loginId + " " + password);
    PrincipalDetails principalDetails = (PrincipalDetails) principalDetailsService.loadUserByUsername(loginId);

    if (!passwordEncoder.matches(password, principalDetails.getPassword())) {
      throw new BadCredentialsException(loginId);
    }

    return new UsernamePasswordAuthenticationToken(principalDetails, principalDetails.getPassword(), principalDetails.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}

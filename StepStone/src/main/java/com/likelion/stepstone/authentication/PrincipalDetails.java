package com.likelion.stepstone.authentication;

import com.likelion.stepstone.user.model.UserEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

  private final UserEntity user;
  private Map<String, Object> attributes;

  public PrincipalDetails(UserEntity user) {
    this.user = user;
  }

  // OAuth 인증 시 가져오는 정보들
  public PrincipalDetails(UserEntity user, Map<String, Object> attributes) {
    this.user = user;
    this.attributes = attributes;
  }

  public UserEntity getUser() {
    return user;
  } // 이거 지워도 되나?

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    user.getRoleList().forEach(r -> {
      authorities.add(() -> r);
    });

    return authorities;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUserId();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public String getName() {
    return user.getName();
  }
}

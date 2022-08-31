package com.likelion.stepstone.authentication.provider;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo{
  private Map<String, Object> attributes; // oauth2.getAttributes()

  public NaverUserInfo(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  @Override
  public String getUserId() {
    return "naver_" + (String) attributes.get("id");
  }

  @Override
  public String getName() {
    return (String) attributes.get("name");
  }
}

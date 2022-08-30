package com.likelion.stepstone.authentication.provider;

import java.util.Map;

public class GoogleUserInfo implements OAuth2UserInfo{

  private Map<String, Object> attributes; // oauth2.getAttributes()

  public GoogleUserInfo(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  @Override
  public String getUserId() {
    return "google_" + (String) attributes.get("sub");
  }

  @Override
  public String getName() {
    return (String) attributes.get("name");
  }
}

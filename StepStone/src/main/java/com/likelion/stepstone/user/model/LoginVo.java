package com.likelion.stepstone.user.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginVo {

  private final String name;

  private final String userId;

  private final String password;

  private final String role;
}
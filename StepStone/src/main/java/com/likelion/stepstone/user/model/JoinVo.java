package com.likelion.stepstone.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Builder
@Getter
@RequiredArgsConstructor
public class JoinVo {
  @Size(min = 2, max = 25, message = "이름은 2글자 이상 25자 미만 입력해 주세요.")
  @NotEmpty(message = "이름은 필수항목 입니다.")
  private final String name;

  @Size(min = 3, max = 25, message = "ID는 3글자 이상 25글자 미만으로 입력해 주세요.")
  @NotEmpty(message = "사용자ID는 필수항목입니다.")
  private final String userId;

  @Size(min = 8, max = 25, message = "비밀번호는 8글자 이상 25글자 미만으로 입력해 주세요.")
  @NotEmpty(message = "비밀번호는 필수항목입니다.")
  private final String password;

  private final String role;
}
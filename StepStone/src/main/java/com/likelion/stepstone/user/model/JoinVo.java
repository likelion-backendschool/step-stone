package com.likelion.stepstone.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Builder
@Getter
@RequiredArgsConstructor
public class JoinVo {
  @Size(min = 2, max = 25, message = "성과 이름을 입력해 주세요.")
  @NotEmpty(message = "이름은 필수항목 입니다.")
  private final String name;

  @Size(min = 3, max = 25, message = "ID는 3글자 이상 25글자 미만으로 입력해 주세요.")
  @NotEmpty(message = "사용자ID는 필수항목입니다.")
  private final String userId;

  @NotEmpty(message = "비밀번호는 필수항목입니다.")
  private final String password;

  private final String role;
}
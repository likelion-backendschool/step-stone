package com.likelion.stepstone.mypage;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MypageForm {
    @NotBlank(message = "새로운 비밀번호를 입력해주세요.")
    private String newPassword;
    @NotBlank(message = "입력하신 새로운 비밀번호와 일치하게 입력해주세요.")
    private String newPasswordConfirm;
}

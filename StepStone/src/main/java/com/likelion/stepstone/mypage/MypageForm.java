package com.likelion.stepstone.mypage;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MypageForm {
    @NotBlank(message = "새로운 비밀번호를 입력해주세요.")
    @Size(min = 8, max = 25, message = "8글자 이상 25글자 미만으로 입력해 주세요.")
    private String newPassword;
    @NotBlank(message = "입력하신 새로운 비밀번호와 일치하게 입력해주세요.")
    @Size(min = 8, max = 25 , message = "")
    private String newPasswordConfirm;
}

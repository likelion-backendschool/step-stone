package com.likelion.stepstone.post;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class PostForm {
    @NotEmpty(message = "제목은 필수항목입니다.")
    private String title;
    @NotEmpty(message = "내용은 필수항목입니다.")
    private String body;
}

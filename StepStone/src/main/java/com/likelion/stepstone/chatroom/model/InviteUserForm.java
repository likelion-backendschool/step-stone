package com.likelion.stepstone.chatroom.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class InviteUserForm {
    @NotEmpty(message = "사용자ID는 필수항목입니다.")
    String userId;
    String chatRoomId;
}

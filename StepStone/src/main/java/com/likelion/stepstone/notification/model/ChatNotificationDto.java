package com.likelion.stepstone.notification.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatNotificationDto extends NotificationDto{
    String ChatRoomId;

    String publisherId;
}

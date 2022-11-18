package com.likelion.stepstone.notification.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatNotificationDto extends NotificationDto{
    String chatRoomId;

    String publisherId;

    public static ChatNotificationDto toDto(ChatNotificationEntity chatNotificationEntity){

        ChatNotificationDto chatNotificationDto = ChatNotificationDto.builder()
                .id(chatNotificationEntity.getId())
                .title(chatNotificationEntity.getTitle())
                .message(chatNotificationEntity.getMessage())
                .checked(chatNotificationEntity.isChecked())
                .userId(chatNotificationEntity.getUserEntity().getUserId())
                .createdAt(chatNotificationEntity.getCreatedAt())
                .notificationType(chatNotificationEntity.getNotificationType().toString())
                .chatRoomId(chatNotificationEntity.getChatRoomEntity().getChatRoomId())
                .publisherId(chatNotificationEntity.getPublisher().getUserId())
                .build();


        return chatNotificationDto;
    }
}

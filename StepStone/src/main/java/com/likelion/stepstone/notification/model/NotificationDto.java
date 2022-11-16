package com.likelion.stepstone.notification.model;

import com.likelion.stepstone.user.model.UserEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@SuperBuilder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {

    private Long id;
    @Setter
    private String title;
    @Setter
    private String message;
    @Setter
    private boolean checked;

    private String userId;

    private LocalDateTime createdAt;
    @Setter
    private String notificationType;

    public static NotificationDto toDto(NotificationEntity entity){
        NotificationDto dto = NotificationDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .message(entity.getMessage())
                .checked(entity.isChecked())
                .userId(entity.getUserEntity().getUserId())
                .createdAt(entity.getCreatedAt())
                .notificationType(entity.getNotificationType().toString())
                .build();

        return dto;
    }

}

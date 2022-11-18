package com.likelion.stepstone.notification.service;


import com.likelion.stepstone.chatroom.ChatRoomJoinRepository;
import com.likelion.stepstone.chatroom.exception.DataNotFoundException;
import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import com.likelion.stepstone.notification.model.*;
import com.likelion.stepstone.notification.repository.NotificationRepository;
import com.likelion.stepstone.user.UserRepository;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;


    public void markAsRead(List<NotificationEntity> notifications) {
        notifications.forEach(NotificationEntity::read);
    }

    public List<NotificationDto> readNewNotifications(String userId){

        Optional<UserEntity> userEntity = userRepository.findByUserId(userId);
        if(userEntity.isEmpty()) return new ArrayList<>();

        List<NotificationEntity> notificationEntities = notificationRepository.findByUserEntityAndChecked(userEntity.get(), false);

        List<NotificationDto> notificationDtos = notificationEntities.stream().map(NotificationDto::toDto).toList();

        return notificationDtos;
    }

    public UserEntity findUserEntityByUserId(String userId){
        UserEntity userEntity = userRepository.findByUserId(userId).orElseThrow(() -> new DataNotFoundException("user not found"));
        return  userEntity;
    }

    public void markAll(String userId) {
        UserEntity userEntity = findUserEntityByUserId(userId);
        List<NotificationEntity> notificationEntities = notificationRepository.findByUserEntityAndChecked(userEntity, false);

        markAsRead(notificationEntities);
    }

    public void markAsRead(Long id) {
        NotificationEntity notificationEntity = notificationRepository.findById(id).orElseThrow(() -> new DataNotFoundException("notification not found"));

        notificationEntity.read();
    }





}

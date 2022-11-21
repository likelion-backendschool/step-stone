package com.likelion.stepstone.notification;

import com.likelion.stepstone.authentication.PrincipalDetails;
import com.likelion.stepstone.chatroom.ChatRoomService;
import com.likelion.stepstone.chatroom.model.InviteUserForm;
import com.likelion.stepstone.notification.handler.ChatNotificationHandler;
import com.likelion.stepstone.notification.model.ChatNotificationDto;
import com.likelion.stepstone.notification.model.NotificationDto;
import com.likelion.stepstone.notification.service.ChatNotificationService;
import com.likelion.stepstone.post.PostService;
import com.likelion.stepstone.user.model.UserEntity;
import com.likelion.stepstone.user.support.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/notification")
public class ChatNotificationController {

    private final ChatNotificationService chatNotificationService;
    private final ChatRoomService chatRoomService;
    private final PostService postService;
    private final ChatNotificationHandler handler;

    @GetMapping("/subscribe/chat/new")
    public String newChatArrived(@AuthUser UserEntity userEntity, Model model, String chatRoomId, String senderId){
//        chatNotificationService.publishNewChat(senderId, chatRoomService.findAllUserInChatRoom(chatRoomId), chatRoomService.findByChatRoomId(chatRoomId));
        List<ChatNotificationDto> dtos = chatNotificationService.readNewNotifications(userEntity);

        model.addAttribute("notifications", dtos);
        model.addAttribute("hasNotification", dtos.size() > 0);
        return "navbar :: #notifications";
    }

    @PostMapping("/invite/publish")
    public String invitePublish(Principal principal, Model model ,@Valid InviteUserForm inviteUserForm){
        if(!chatRoomService.isUserExist(inviteUserForm.getUserId())){
            model.addAttribute("error", "user not found");
            model.addAttribute("alertMessage", "유효한 아이디가 아닙니다.");
            return "chat/room :: #alertMessage";
        }
        if(chatRoomService.isUserAlreadyIn(inviteUserForm.getUserId(), inviteUserForm.getChatRoomId())){
            model.addAttribute("error", "user is already in chat room");
            model.addAttribute("alertMessage", "사용자가 이미 채팅방에 존재합니다.");
            return "chat/room :: #alertMessage";
        }

        chatRoomService.invite(inviteUserForm.getChatRoomId(), inviteUserForm.getUserId(), principal.getName());

        model.addAttribute("alertMessage", "초대가 완료 되었습니다.");
        return "chat/room :: #alertMessage";
    }

    @PostMapping("/post/inquiry/publish")
    public String postInquiryPublish(Principal principal ,@RequestParam Long postCid){
        chatRoomService.inquire(principal.getName(), postService.findByPostCid(postCid));

        return "redirect:/post/detail/" + postCid;
    }

    @PostMapping("/workspace/inquiry/publish")
    public String workspaceInquiryPublish(Principal principal ,@RequestParam Long postCid, @RequestParam Long workspaceCid){
        chatRoomService.inquire(principal.getName(), postService.findByPostCid(postCid));

        return "redirect:/workspace/detail/" + workspaceCid;
    }

    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect(@AuthUser UserEntity user) {
        SseEmitter emitter = new SseEmitter();
        handler.add(emitter);
//        handler.send(user.getName(), chatNotificationService.getAllNotification(user));
        return ResponseEntity.ok(emitter);
    }
}

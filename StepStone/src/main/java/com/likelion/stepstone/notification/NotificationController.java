package com.likelion.stepstone.notification;

import com.likelion.stepstone.chat.event.ChatSendEvent;
import com.likelion.stepstone.chat.model.ChatDto;
import com.likelion.stepstone.chatroom.ChatRoomService;
import com.likelion.stepstone.chatroom.model.InviteUserForm;
import com.likelion.stepstone.notification.model.NotificationDto;
import com.likelion.stepstone.notification.model.NotificationEntity;
import com.likelion.stepstone.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;
    private final ChatRoomService chatRoomService;
    private final PostService postService;

//    @GetMapping("/read/new")
//    public String readNewNotification(Principal principal, Model model){
//        MarkingNotifications markingNotifications = new MarkingNotifications();
//
//        List<NotificationDto> dtos = notificationService.readNewNotifications(principal.getName());
//
//        markingNotifications.setNotifications(dtos);
//        model.addAttribute("notifications",markingNotifications);
//
//        return "navbar :: #dropdown-menu";
//    }

    /**
     * 페이지 refresh를 실행하지 않기 위해
     * ajax post 함수로 데이터를 전송함
     *
     * 브라우저 콘솔에 404 or 500 에러가 발생하지만, 영향은 없다.
     * @param id
     * @return
     */
    @PostMapping("/mark")
    public String markAsRead(Model model, Long id , boolean isEmpty){
//        List<NotificationDto> dtos = markingNotifications.getNotifications();
        notificationService.markAsRead(id);
        if(isEmpty){
            model.addAttribute("hasNotification", false);
            return "navbar :: #notifications";
        }
        return "markNotification";
    }

    @PostMapping("/mark/all")
    public String markAll(Principal principal, Model model){
        notificationService.markAll(principal.getName());
        model.addAttribute("hasNotification", false);
        return "navbar :: #notifications";
    }

    @GetMapping("/subscribe/chat/new")
    public String newChatArrived(Principal principal, Model model, String chatRoomId, String senderId){
        notificationService.publishNewChat(senderId, chatRoomService.findAllUserInChatRoom(chatRoomId), chatRoomService.findByChatRoomId(chatRoomId));
        List<NotificationDto> dtos = notificationService.readNewNotifications(principal.getName());

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

    @PostMapping("/inquiry/publish")
    public String inquiryPublish(Principal principal,Model model, @RequestParam Long postCid){
        chatRoomService.inquire(principal.getName(), postService.findByPostCid(postCid));

        return "redirect:/post/detail/" + postCid;
    }




//    @PostMapping("/chatroom/enter/")
//    public String registerOnlineChatUser(Principal principal, String roomId){
//        notificationService.registerOnlineChatUser(principal.getName(), roomId);
//
//        return "onlineChatRoom";
//    }
}

package com.likelion.stepstone.chatroom;

import com.likelion.stepstone.chat.ChatService;
import com.likelion.stepstone.chat.model.ChatDto;
import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import com.likelion.stepstone.chatroom.model.ChatRoomForm;
import com.likelion.stepstone.chatroom.model.ChatRoomDto;
import com.likelion.stepstone.chatroom.model.InviteUserForm;
import com.likelion.stepstone.notification.NotificationService;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ChatService chatService;
    private final NotificationService notificationService;

    @GetMapping("/room")
    public String getRoom(Principal principal, Model model, ChatRoomForm chatRoomForm, InviteUserForm inviteUserForm) {
        List<ChatRoomDto> rooms = chatRoomService.findAll(principal.getName()); // principal의 name은 userEntity의 user_id
        List<ChatDto> chats = new ArrayList<>();
        String imageUrl = chatRoomService.getChatRoomImageUrl();
        if(!rooms.isEmpty()){
            ChatRoomDto firstChatRoom = rooms.get(0);
            chats.addAll(chatService.getHistories(firstChatRoom.getChatRoomId()));

            List<String> allRoomId = chatRoomService.findAllRoomId(principal.getName());
//            notificationService.registerOnlineChatUser(principal.getName(), firstChatRoom.getChatRoomId());
            imageUrl = chatRoomService.findChatImageUrlByRoomId(firstChatRoom.getChatRoomId());
            String roomName = chatRoomService.findChatRoomNameByRoomId(firstChatRoom.getChatRoomId());

            model.addAttribute("creationAvatar", chatRoomService.getCreationAvatar(principal.getName(), firstChatRoom.getChatRoomId()));
            model.addAttribute("allRoomId", allRoomId);
            model.addAttribute("roomName", roomName);
            model.addAttribute("roomId", firstChatRoom.getChatRoomId());
        }
        model.addAttribute("roomImageUrl", imageUrl);
        model.addAttribute("rooms", rooms);
        model.addAttribute("chats", chats);
        model.addAttribute("senderId", principal.getName());
        model.addAttribute("name", chatRoomService.getUsername(principal.getName()));

        return "chat/room";
    }

    @GetMapping("/history") // 저장된 채팅 내역 조회
    String getHistory(Principal principal, Model model, String roomId) {
        List<ChatDto> chats = chatService.getHistories(roomId);

//        notificationService.removeOnlineChatUser(principal.getName(), beforeRoomId);
//        notificationService.registerOnlineChatUser(principal.getName(), roomId);


        model.addAttribute("creationAvatar", chatRoomService.getCreationAvatar(principal.getName(), roomId));

        model.addAttribute("chats", chats);
        model.addAttribute("roomId", roomId);
        model.addAttribute("senderId", principal.getName());
        model.addAttribute("name", chatRoomService.getUsername(principal.getName()));
        return "chat/room :: #chats";
    }

    @PostMapping("/room/create")
    public String createRoom(Principal principal, Model model, ChatRoomForm chatRoomForm) {

        ChatRoomDto chatRoomDto = ChatRoomDto.builder()
                .chatRoomId(UUID.randomUUID().toString())
                .roomName(chatRoomForm.getRoomName())
                .postCid(chatRoomForm.getPostCid())
                .userCount(0)
                .build();
        chatRoomService.create(chatRoomDto, principal.getName());

        List<ChatRoomDto> rooms = chatRoomService.findAll(principal.getName());
        model.addAttribute("rooms", rooms);
        return "chat/room :: #chatRoomTable";
    }

    @PostMapping("/room/invite")
    public String inviteRoom(Principal principal, Model model, @Valid InviteUserForm inviteUserForm) {
//        List<ChatDto> chats = chatService.getHistories(inviteUserForm.getChatRoomId());
//        List<ChatRoomDto> rooms = chatRoomService.findAll(principal.getName());
//
//        model.addAttribute("chats", chats);
//        model.addAttribute("rooms", rooms);

        if(!chatRoomService.isUserExist(inviteUserForm.getUserId())){
            model.addAttribute("error", "user not found");
            model.addAttribute("message", "유효한 아이디가 아닙니다.");
            return "chat/room :: #message";
        }

        chatRoomService.invite(inviteUserForm.getChatRoomId(), inviteUserForm.getUserId());
        model.addAttribute("message", "초대가 완료 되었습니다.");
        return "chat/room :: #message";

    }
    // 채팅방 입장 화면
    @GetMapping("/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "chat/roomdetail";
    }

    @GetMapping("/room/represent")
    public String getRoomInfo(Model model, String roomId){
        String imageUrl = chatRoomService.findChatImageUrlByRoomId(roomId);
        String roomName = chatRoomService.findChatRoomNameByRoomId(roomId);

        model.addAttribute("roomImageUrl", imageUrl);
        model.addAttribute("roomName", roomName);

        return "chat/room :: #representRoom";
    }

    @GetMapping("/room/search")
    public String searchChatRoom(Principal principal, Model model, String chatRoomName){
        List<ChatRoomDto> rooms = chatRoomService.searchByUserIdAndChatRoomName(principal.getName(), chatRoomName);
        model.addAttribute("rooms", rooms);
        return "chat/room :: #chatRoomTable";
    }


}

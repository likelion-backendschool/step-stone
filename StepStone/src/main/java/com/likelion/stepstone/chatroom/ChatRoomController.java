package com.likelion.stepstone.chatroom;

import com.likelion.stepstone.chat.ChatService;
import com.likelion.stepstone.chat.model.ChatDto;
import com.likelion.stepstone.chatroom.model.ChatRoomForm;
import com.likelion.stepstone.chatroom.model.ChatRoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/room")
    public String getRoom(Principal principal, Model model, ChatRoomForm chatRoomForm) {
        List<ChatRoomDto> rooms = chatRoomService.findAll(principal.getName()); // principal의 name은 userEntity의 user_id
        List<ChatDto> chats = new ArrayList<>();

        if(!rooms.isEmpty()){
            ChatRoomDto firstChatRoom = rooms.get(0);
            chats.addAll(chatService.getHistories(firstChatRoom.getChatRoomId()));
        }

        model.addAttribute("rooms", rooms);
        model.addAttribute("chats", chats);

        return "chat/room";
    }

    @PostMapping("/room/create")
    public String createRoom(Principal principal, Model model, ChatRoomForm chatRoomForm) {

        ChatRoomDto chatRoomDto = ChatRoomDto.builder()
                .chatRoomId(UUID.randomUUID().toString())
                .roomName(chatRoomForm.getRoomName())
                .postCid(chatRoomForm.getPostCid())
                .userCount(0)
                .build();
        chatRoomService.create(chatRoomDto);

        List<ChatRoomDto> rooms = chatRoomService.findAll(principal.getName());
        model.addAttribute("rooms", rooms);
        return "chat/room :: #chatRoomTable";
    }
    // 채팅방 입장 화면
    @GetMapping("/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "chat/roomdetail";
    }
}

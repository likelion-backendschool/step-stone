package com.likelion.stepstone.chatroom;

import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import com.likelion.stepstone.chatroom.model.ChatRoomForm;
import com.likelion.stepstone.chatroom.model.ChatRoomDto;
import com.likelion.stepstone.chatroom.model.ChatRoomVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/room")
    public String getRoom(Model model, ChatRoomForm chatRoomForm) {
        List<ChatRoomDto> rooms = chatRoomService.findAll();

        model.addAttribute("rooms", rooms);

        return "chat/room";
    }

    @PostMapping("/room/create")
    public String createRoom(ChatRoomForm chatRoomForm) {

        ChatRoomDto chatRoomDto = ChatRoomDto.builder()
                .chatRoomId(UUID.randomUUID())
                .roomName(chatRoomForm.getRoomName())
                .postCid(chatRoomForm.getPostCid())
                .userCount(0)
                .build();
        chatRoomService.create(chatRoomDto);
        return "redirect:/chat/room";
    }
    // 채팅방 입장 화면
    @GetMapping("/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "chat/roomdetail";
    }
}

package com.likelion.stepstone.chatroom;

import com.likelion.stepstone.chatroom.model.ChatRoomForm;
import com.likelion.stepstone.chatroom.model.ChatRoomDto;
import com.likelion.stepstone.chatroom.model.ChatRoomVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat/room")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/")
    public String getRoom(Model model) {
        return "chat/room";
    }

    @PostMapping("/create")
    @ResponseBody
    public ChatRoomVo createRoom(ChatRoomForm chatRoomForm) {

        ChatRoomDto chatRoomDto = ChatRoomDto.builder()
                .chatRoomId(UUID.randomUUID())
                .roomName(chatRoomForm.getRoomName())
                .postCid(chatRoomForm.getPostCid())
                .userCount(0)
                .build();

        return chatRoomService.create(chatRoomDto);
    }
    // 채팅방 입장 화면
    @GetMapping("/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "chat/roomdetail";
    }
}

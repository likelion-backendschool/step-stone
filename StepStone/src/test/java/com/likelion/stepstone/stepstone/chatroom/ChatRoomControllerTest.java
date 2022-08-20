package com.likelion.stepstone.stepstone.chatroom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelion.stepstone.chatroom.ChatRoomController;
import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import com.likelion.stepstone.chatroom.model.ChatRoomForm;
import com.likelion.stepstone.chatroom.model.ChatRoomVo;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ChatRoomControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ChatRoomController chatRoomController;

    @BeforeEach
    void setUpMockMvc(){
        mockMvc = MockMvcBuilders.standaloneSetup(chatRoomController)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    /**
     * 채팅방 Create 테스트
     * 채팅방 이름과, 관련된 게시글 Cid를 입력하여 Post요청을 보낸다.
     *
     * 반환되는 ChatRoomVo 클래스 객체를 String Json 객체로 return 된다.
     * String Json 에서 key로 value를 추출하여 값을 비교한다.
     * 
     * @throws Exception
     */
    @Test
    void createTest() throws Exception {
        //given
        ChatRoomForm chatRoomForm = new ChatRoomForm();
        chatRoomForm.setRoomName("채팅방");
        chatRoomForm.setPostCid(1l);

        //when
        MvcResult mvcResult = this.mockMvc.perform(post("/chat/room/create?roomName=채팅방&postCid=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        //then
        String str = mvcResult.getResponse().getContentAsString();
        System.out.println("-----------------");
        System.out.println("반환 값 : " + str);

        JSONObject obj = new JSONObject(str);
        String roomName = obj.getString("roomName");
        Long postCid = obj.getLong("postCid");


        assertThat(roomName.equals("채팅방"));
        assertThat(postCid.equals(1l));

    }

}

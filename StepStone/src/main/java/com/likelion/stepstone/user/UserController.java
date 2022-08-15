package com.likelion.stepstone.user;

import com.likelion.stepstone.user.model.UserDto;
import com.likelion.stepstone.user.model.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = "/user", method = {RequestMethod.GET, RequestMethod.POST})
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // user 데이터 추가
    @GetMapping("/create")
    public String createUser(@RequestParam UUID userId, @RequestParam UUID password, @RequestParam String name, Model model) {
        UserDto userDto = UserDto.builder()
                .userId(userId)
                .password(password)
                .name(name)
                .build();

        userService.createUser(userDto);
        model.addAttribute("userId", userDto.getUserId());
        model.addAttribute("password", userDto.getPassword());
        model.addAttribute("name", userDto.getName());

        return "user/create";
    }

    // user 데이터 리스트로 확인
    @GetMapping("/list")
    public String listUser(Model model) {
        List<UserDto> userList = userService.getUserlist();

        model.addAttribute("userList", userList);

        return "user/list";
    }

    // user 데이터 삭제
    @DeleteMapping("/delete/{userId}")
    public String deleteUser(@PathVariable("userId") UUID userId, Model model) {
        userService.deleteUser(userId);

        // alert를 띄우도록 하기
        model.addAttribute("message", "삭제되었습니다.");
//        model.addAttribute("searchUrl", "user/list");

        return "user/message";
    }

    // user 데이터 수정
    @GetMapping("/update/{userId}")
    public String editUserInfo(@PathVariable("userId") UUID userId, Model model) {
        UserDto userDto = userService.getUserInfo(userId);

        if (userDto == null) {
            model.addAttribute("message", "해당 데이터가 존재하지 않습니다.");
            return "user/message";
        }
        else {
            model.addAttribute("userDto", userDto);
            return "user/update";
        }
    }

    @PutMapping("/update/{userId}")
    public String updateUser(UserDto userDto) {
        userService.updateUser(userDto);

        return "redirect:/";
    }
}

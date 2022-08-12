package com.likelion.stepstone.user;

import com.likelion.stepstone.user.model.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

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

    @GetMapping("/list")
    public String listUser(Model model) {
        List<UserDto> userList = userService.getUserlist();

        model.addAttribute("userList", userList);

        return "user/list";
    }

}

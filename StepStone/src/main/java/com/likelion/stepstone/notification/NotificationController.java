package com.likelion.stepstone.notification;

import com.likelion.stepstone.notification.model.NotificationDto;
import com.likelion.stepstone.notification.model.NotificationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/read/new")
    public String readNewNotification(Principal principal, Model model){
        List<NotificationDto> dtos = notificationService.readNewNotifications(principal.getName());

        model.addAttribute("notifications",dtos);

        return "navbar :: #dropdown-menu";
    }
}

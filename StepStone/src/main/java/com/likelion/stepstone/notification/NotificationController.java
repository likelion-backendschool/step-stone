package com.likelion.stepstone.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RequiredArgsConstructor
@Controller
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;

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
     * 브라우저 콘솔에 404 에러가 발생하지만, 영향은 없다.
     * @param id
     * @return
     */
    @PostMapping("/mark")
    public String markAsRead(Long id ){
//        List<NotificationDto> dtos = markingNotifications.getNotifications();

        System.out.println(id);
        return "redirect:";
    }
}

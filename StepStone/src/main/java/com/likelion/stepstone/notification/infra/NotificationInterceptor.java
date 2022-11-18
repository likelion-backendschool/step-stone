package com.likelion.stepstone.notification.infra;

import com.likelion.stepstone.authentication.PrincipalDetails;
import com.likelion.stepstone.chatroom.ChatRoomService;
import com.likelion.stepstone.notification.model.ChatNotificationDto;
import com.likelion.stepstone.notification.model.ChatNotificationEntity;
import com.likelion.stepstone.notification.repository.ChatNotificationRepository;
import com.likelion.stepstone.notification.repository.NotificationBaseRepository;
import com.likelion.stepstone.notification.repository.NotificationRepository;
import com.likelion.stepstone.notification.service.NotificationService;
import com.likelion.stepstone.notification.model.NotificationDto;
import com.likelion.stepstone.notification.model.NotificationEntity;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NotificationInterceptor implements HandlerInterceptor {

    private final ChatNotificationRepository notificationRepository;

    private final NotificationService notificationService;
    private final ChatRoomService chatRoomService;

    /**
     * 1. 리다이렉트가 아니고
     * 2. 인증 정보가 존재하고 PrincipalDetail 타입일 때
     * 3. User 정보를 획득하여
     * 4. 알림 정보를 조회하고
     * 5. Model로 전달합니다.
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (modelAndView != null
//                && !isRedirectView(modelAndView)  // (1)
                && authentication != null && isTypeOfUserAccount(authentication)) { // (2)
            UserEntity userEntity = ((PrincipalDetails) authentication.getPrincipal()).getUser(); // (3)
//            notificationService.markAsRead(notificationEntities);
//            notificationEntities.forEach(NotificationEntity::read);
//            long count = notificationDtos.size();
            String uri = request.getRequestURI();
            String name =  SecurityContextHolder.getContext().getAuthentication().getName();
            List<String> allRoomId = chatRoomService.findAllRoomId(name);
            modelAndView.addObject("allRoomId", allRoomId);
            if (uri.contains("notification")) return;
            setNotificationsOnUI(userEntity, modelAndView);
        }
    }

    private void setNotificationsOnUI(UserEntity userEntity, ModelAndView modelAndView){
        long count = notificationRepository.countByUserEntityAndChecked(userEntity, false); // (4)
        List<ChatNotificationEntity> notificationEntities = notificationRepository.findByUserEntityAndChecked(userEntity, false);
        List<ChatNotificationDto> notificationDtos = notificationEntities.stream().map(ChatNotificationDto::toDto).toList();

        modelAndView.addObject("hasNotification", count > 0); // (5)
        modelAndView.addObject("notifications", notificationDtos);
    }

    private boolean isRedirectView(ModelAndView modelAndView) {
        Optional<ModelAndView> optionalModelAndView = Optional.ofNullable(modelAndView);
        return startsWithRedirect(optionalModelAndView) || isTypeOfRedirectView(optionalModelAndView);
    }

    private Boolean startsWithRedirect(Optional<ModelAndView> optionalModelAndView) {
        return optionalModelAndView.map(ModelAndView::getViewName)
                .map(viewName -> viewName.startsWith("redirect:"))
                .orElse(false);
    }

    private Boolean isTypeOfRedirectView(Optional<ModelAndView> optionalModelAndView) {
        return optionalModelAndView.map(ModelAndView::getView)
                .map(v -> v instanceof RedirectView)
                .orElse(false);
    }

    private boolean isTypeOfUserAccount(Authentication authentication) {
        return authentication.getPrincipal() instanceof
                PrincipalDetails;
    }
}
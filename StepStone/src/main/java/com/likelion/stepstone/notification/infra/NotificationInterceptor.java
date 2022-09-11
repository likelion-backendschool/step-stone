package com.likelion.stepstone.notification.infra;

import com.likelion.stepstone.authentication.PrincipalDetails;
import com.likelion.stepstone.notification.NotificationRepository;
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
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NotificationInterceptor implements HandlerInterceptor {

    private final NotificationRepository notificationRepository;

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
        if (modelAndView != null && !isRedirectView(modelAndView)  // (1)
                && authentication != null && isTypeOfUserAccount(authentication)) { // (2)
            UserEntity userEntity = ((PrincipalDetails) authentication.getPrincipal()).getUser(); // (3)
            long count = notificationRepository.countByUserEntityAndChecked(userEntity, false); // (4)
            modelAndView.addObject("hasNotification", count > 0); // (5)
        }
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
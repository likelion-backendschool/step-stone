package com.likelion.stepstone.authentication;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {
  @RequestMapping("/error")
  public String handleError(HttpServletRequest request, Model model) {
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

    if (status != null) {
      Integer statusCode = Integer.valueOf(status.toString());

      if(statusCode == HttpStatus.NOT_FOUND.value()) {
        model.addAttribute("msg", "찾으시는 주소는 없는 주소입니다.");
      }
      else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
        model.addAttribute("msg", "내부 서버 오류입니다.");
      }
      else if(statusCode == 403) {
        model.addAttribute("msg", "권한이 없습니다.");
      }
      else {
        model.addAttribute("msg", status.toString());
      }
    }
    return "error";
  }
}

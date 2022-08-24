package com.likelion.stepstone.workspaces;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/workspace")
@Controller
public class WorkSpaceController {

   // @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(WorkSpaceForm workSpaceForm) {

        return "workspace/create";
    }
}

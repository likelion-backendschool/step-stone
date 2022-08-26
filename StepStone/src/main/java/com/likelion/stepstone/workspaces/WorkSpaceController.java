package com.likelion.stepstone.workspaces;



import lombok.AllArgsConstructor;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/workspace")
@Controller
public class WorkSpaceController {

    @GetMapping("/create")
    public String create(WorkSpaceForm workSpaceForm) {
        return "workspace/workspace_form";
    }


    @RequestMapping("/list")
//    @ResponseBody
    public String list() {
        return "workspace/workspace_list";
    }
    @RequestMapping("/detail")
//    @ResponseBody
    public String detail() {
        return "workspace/workspace_detail";
    }
    @RequestMapping("/modify")
//    @ResponseBody
    public String modify() {
        return "workspace/workspace_modify";
    }
}
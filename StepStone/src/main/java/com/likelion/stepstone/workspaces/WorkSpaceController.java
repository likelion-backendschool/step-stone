package com.likelion.stepstone.workspaces;

import com.likelion.stepstone.authentication.PrincipalDetails;
import com.likelion.stepstone.user.UserService;
import com.likelion.stepstone.workspaces.model.WorkSpaceDto;
import com.likelion.stepstone.workspaces.model.WorkSpaceEntity;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/workspace")
@Controller
public class WorkSpaceController {

    private final WorkSpaceService workSpaceService;
    private final UserService userService;

    public WorkSpaceController(WorkSpaceService workSpaceService,UserService userService) {
        this.workSpaceService = workSpaceService;
        this.userService = userService;
    }

    @GetMapping("/create/{id}")
    public String create(WorkSpaceForm workSpaceForm,@PathVariable long id, Model model) {

        model.addAttribute("id", id);
        return "workspace/workspace_form";
    }

    @PostMapping("/create/{id}")
    public String createPost(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model, WorkSpaceForm workSpaceForm,@PathVariable long id) {

        //유효성 체크
        boolean hasError = false;

        if (workSpaceForm.getTitle() == null || workSpaceForm.getTitle().trim().length() == 0) {
            model.addAttribute("titleErrorMsg", "제목을 입력해주세요");
            hasError = true;
        }

        if (workSpaceForm.getBody() == null || workSpaceForm.getBody().trim().length() == 0) {
            model.addAttribute("bodyErrorMsg", "내용을 입력해주세요");
            hasError = true;
        }

        if (hasError) {
            model.addAttribute("workSpaceForm", workSpaceForm);
            return "workspace/workspace_form";
        }

        WorkSpaceDto workSpaceDto = WorkSpaceDto.builder()
                .title(workSpaceForm.getTitle())
                .body(workSpaceForm.getBody())
                .postCid(id)
                .build();

        workSpaceService.create(workSpaceDto,principalDetails);

        return "redirect:/workspace/list";
    }

    @GetMapping("/list")
    public String list(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<WorkSpaceEntity> paging = workSpaceService.getList(page);
        model.addAttribute("paging", paging);
        return "workspace/workspace_list";
    }

    @PostAuthorize("hasRole('ROLE_ADMIN') or #workSpaceForm.userId == authentication.principal.username")
    @GetMapping("/modify/{workspaceCid}")
    public String questionModifyGet(@PathVariable long workspaceCid , WorkSpaceForm workSpaceForm) {

       // @Valid WorkSpaceForm workSpaceForm
        WorkSpaceDto workSpaceDto = workSpaceService.getWorkSpaceDto(workspaceCid);
        workSpaceForm.setTitle(workSpaceDto.getTitle());
        workSpaceForm.setBody(workSpaceDto.getBody());
        workSpaceForm.setUserId(workSpaceDto.getUser().getUserId());
        return "workspace/workspace_form";
    }

    @PostMapping("/modify/{workspaceCid}")
    public String questionModifyPost(@PathVariable long workspaceCid , WorkSpaceForm workSpaceForm) {

        WorkSpaceDto workSpaceDto = workSpaceService.getWorkSpaceDto(workspaceCid);
        workSpaceService.modify(workSpaceDto, workSpaceForm.getTitle(), workSpaceForm.getBody());

        return "redirect:/workspace/detail/{workspaceCid}";
    }

    @GetMapping("/detail/{workspaceCid}")
    public String detail(Model model, @PathVariable  long workspaceCid) {
        WorkSpaceDto workSpaceDto = workSpaceService.getWorkSpaceDto(workspaceCid);
        model.addAttribute("workSpaceEntity", workSpaceDto);
        return "workspace/workspace_detail";
    }

    @GetMapping("/delete/{workspaceCid}")
    public String delete( @PathVariable long workspaceCid) {
        WorkSpaceDto workSpaceDto = workSpaceService.getWorkSpaceDto(workspaceCid);

        workSpaceService.delete(workSpaceDto);

        return "redirect:/workspace/list";
    }

}
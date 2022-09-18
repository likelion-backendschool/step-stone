package com.likelion.stepstone.projects;

import com.likelion.stepstone.projects.model.ProjectDto;
import com.likelion.stepstone.projects.model.ProjectEntity;
import com.likelion.stepstone.user.UserService;
import com.likelion.stepstone.user.model.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RequestMapping("/project")
@Controller
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String create(ProjectForm projectForm) {
        return "project/project_form";
    }

    @PostMapping("/create")
    public String createProject(Principal principal, Model model, ProjectForm projectForm) {

        //유효성 체크
        boolean hasError = false;

        if (projectForm.getTitle() == null || projectForm.getTitle().trim().length() == 0) {
            model.addAttribute("titleErrorMsg", "제목을 입력해주세요");
            hasError = true;
        }

        if (projectForm.getBody() == null || projectForm.getBody().trim().length() == 0) {
            model.addAttribute("bodyErrorMsg", "내용을 입력해주세요");
            hasError = true;
        }

        if (hasError) {
            model.addAttribute("projectForm", projectForm);
            return "project/project_form";
        }

        UserDto user = userService.getUser(principal.getName());

        ProjectDto projectDto = ProjectDto.builder()
                .title(projectForm.getTitle())
                .body(projectForm.getBody())
                .build();

        projectService.create(projectDto,user);

        return "redirect:/project/list";
    }

    @GetMapping("/list")
    public String list(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<ProjectEntity> paging = projectService.getList(page);
        model.addAttribute("paging", paging);
        return "project/project_list";
    }

    @PostAuthorize("hasRole('ROLE_ADMIN') or #projectForm.userId == authentication.principal.username")
    @GetMapping("/modify/{projectCid}")
    public String projectModifyGet(@PathVariable long projectCid , ProjectForm projectForm) {

        ProjectDto projectDto = projectService.getProjectDto(projectCid);

        projectForm.setTitle(projectDto.getTitle());
        projectForm.setBody(projectDto.getBody());
        projectForm.setUserId(projectDto.getUser().getUserId());
        return "project/project_form";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #projectForm.userId == authentication.principal.username")
    @PostMapping("/modify/{projectCid}")
    public String projectModifyPost(@PathVariable long projectCid , ProjectForm projectForm) {

        ProjectDto projectDto = projectService.getProjectDto(projectCid);
        projectService.modify(projectDto, projectForm.getTitle(), projectForm.getBody());

        return "redirect:/project/detail/{projectCid}";
    }

    @GetMapping("/detail/{projectCid}")
    public String detail(Model model, @PathVariable  long projectCid) {
        ProjectDto projectDto = projectService.getProjectDto(projectCid);

        model.addAttribute("projectEntity", projectDto);
        return "project/project_detail";

    }

    @GetMapping("/delete/{projectCid}")
    public String delete( @PathVariable long projectCid) {
        ProjectDto projectDto = projectService.getProjectDto(projectCid);

        projectService.delete(projectDto);

        return "redirect:/workspace/list";
    }
}



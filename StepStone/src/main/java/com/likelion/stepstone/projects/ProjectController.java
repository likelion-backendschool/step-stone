package com.likelion.stepstone.projects;

import com.likelion.stepstone.authentication.PrincipalDetails;
import com.likelion.stepstone.projects.model.ProjectDto;
import com.likelion.stepstone.projects.model.ProjectEntity;
import com.likelion.stepstone.workspaces.WorkSpaceService;
import com.likelion.stepstone.workspaces.model.WorkSpaceDto;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/project")
@Controller
public class ProjectController {

    private final ProjectService projectService;

    private final  WorkSpaceService workspaceService;
    public ProjectController(ProjectService projectService, WorkSpaceService workspaceService) {
        this.projectService = projectService;
        this.workspaceService = workspaceService;
    }

    @GetMapping("/create/{id}")
    public String create(ProjectForm projectForm, @PathVariable long id, Model model) {

        ProjectDto projectDto = projectService.getProjectDto(id);   //id = workspaceCid

        if(projectDto != null){

            Long projectCid = projectDto.getProjectCid();
            model.addAttribute("msg","이미 완료글을 작성하였습니다. 게시글 수정을 이용해주세요.");
            model.addAttribute("projectCid", projectCid);
            return "project/alert2";
        }

        model.addAttribute("id", id);
        return "project/project_form";
    }

    @PostMapping("/create/{id}")
    public String createProject(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model, ProjectForm projectForm, @PathVariable long id) {

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

        WorkSpaceDto workSpaceDto = workspaceService.getWorkspaceDto(id);
        Long postCid = workSpaceDto.getPostCid();

        ProjectDto projectDto = ProjectDto.builder()
                .title(projectForm.getTitle())
                .body(projectForm.getBody())
                .workspaceCid(id)
                .postCid(postCid)
                .build();

        projectService.create(projectDto,principalDetails);

        return "redirect:/project/list";
    }

    @GetMapping("/list")
    public String list(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<ProjectEntity> paging = projectService.getList(page);
        model.addAttribute("paging", paging);
        return "project/project_list";
    }
    @GetMapping("/list/{id}")
    public String listWithId(Model model, @RequestParam(defaultValue = "0") int page,@PathVariable Long id) {
        Page<ProjectEntity> paging = projectService.getListWithId(id, page);
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

    @PostMapping("/modify/{projectCid}")
    public String projectModifyPost(@PathVariable long projectCid , ProjectForm projectForm) {

        ProjectDto projectDto = projectService.getProjectDto(projectCid);
        projectService.modify(projectDto, projectForm.getTitle(), projectForm.getBody());

        return "redirect:/project/detail/{projectCid}";
    }

    @GetMapping("/detail/now/{projectCid}")
    public String nowDetail(Model model, @PathVariable  long projectCid) {
        ProjectDto projectDto = projectService.getNowProjectDto(projectCid);

        model.addAttribute("projectEntity", projectDto);
        return "project/project_detail";

    }

    @GetMapping("/detail/{workspaceCid}")
    public String detail(Model model, @PathVariable long workspaceCid) {
        ProjectDto projectDto = projectService.getProjectDto(workspaceCid);
        if(projectDto == null){
            model.addAttribute("msg","완성된 프로젝트가 없습니다.");
            model.addAttribute("workspaceCid", workspaceCid);
            return "project/alert";
        }

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
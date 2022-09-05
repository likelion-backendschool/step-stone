package com.likelion.stepstone.projects;


import com.likelion.stepstone.projects.model.ProjectDto;
import com.likelion.stepstone.projects.model.ProjectEntity;
import com.likelion.stepstone.workspaces.WorkSpaceForm;
import com.likelion.stepstone.workspaces.model.WorkSpaceDto;
import com.likelion.stepstone.workspaces.model.WorkSpaceEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/project")
@Controller
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/create")
    public String create(ProjectForm projectForm) {
        return "project/project_form";
    }

    @PostMapping("/create")
    public String createProject(Model model, ProjectForm projectForm) {

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

     // 객체 저장방법 고르기
     //  questionService.create(questionFrom.getSubject(), questionFrom.getContent());

        ProjectDto projectDto = ProjectDto.builder()
                .title(projectForm.getTitle())
                .body(projectForm.getBody())
                .build();

        projectService.create(projectDto);

        return "redirect:/project/list";
    }

    @GetMapping("/list")
    public String list(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<ProjectEntity> paging = projectService.getList(page);
        model.addAttribute("paging", paging);
        return "project/project_list";
    }

    @GetMapping("/modify/{projectCid}")
    public String projectModifyGet(@PathVariable long projectCid , ProjectForm projectForm) {

        ProjectEntity projectEntity = projectService.getProjectEntity(projectCid);

        projectForm.setTitle(projectEntity.getTitle());
        projectForm.setBody(projectEntity.getBody());
        return "project/project_form";
    }
    @PostMapping("/modify/{projectCid}")
    public String projectModifyPost(@PathVariable long projectCid , ProjectForm projectForm) {

        ProjectEntity projectEntity = projectService.getProjectEntity(projectCid);
        projectService.modify(projectEntity, projectForm.getTitle(), projectForm.getBody());

        return "redirect:/project/detail/{projectCid}";
    }

    @GetMapping("/detail/{projectCid}")
    public String detail(Model model, @PathVariable  long projectCid) {
        ProjectEntity projectEntity = projectService.getProjectEntity(projectCid);
        model.addAttribute("newLineChar", '\n');
        model.addAttribute("projectEntity", projectEntity);
        return "project/project_detail";
    }

    @GetMapping("/delete/{projectCid}")
    public String delete( @PathVariable long projectCid) {
        ProjectEntity projectEntity = projectService.getProjectEntity(projectCid);

        projectService.delete(projectEntity);

        return "redirect:/workspace/list";
    }
}



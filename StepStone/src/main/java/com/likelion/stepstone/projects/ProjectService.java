package com.likelion.stepstone.projects;

import com.likelion.stepstone.Util.DataNotFoundException;
import com.likelion.stepstone.projects.model.ProjectDto;
import com.likelion.stepstone.projects.model.ProjectEntity;
import com.likelion.stepstone.user.model.UserDto;
import com.likelion.stepstone.user.model.UserEntity;
import com.likelion.stepstone.workspaces.model.WorkSpaceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

public class ProjectService {
    public final ProjectRepository projectRepository;
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void create(ProjectDto projectDto, UserDto userDto) {
        ProjectEntity projectEntity = ProjectEntity.toEntity(projectDto);
        UserEntity user = UserEntity.toEntity(userDto);

        projectEntity.setTitle(projectDto.getTitle());
        projectEntity.setBody(projectDto.getBody());
        projectEntity.setCreatedAt(LocalDateTime.now());
        projectEntity.setUser(user);

        projectRepository.save(projectEntity);
    }

    public Page<ProjectEntity> getList(int page) {
        Pageable pageable = getPageable(page, 5, Sort.by(Sort.Direction.DESC, "projectCid"));
        return projectRepository.findAll(pageable);
    }

    private Pageable getPageable(int page, int size, Sort DESC) {
        Pageable pageable = PageRequest.of(page, size, DESC);
        return pageable;
    }

public ProjectEntity getProjectEntity(Long projectCid) {
    return projectRepository.findByProjectCid(projectCid)
            .orElseThrow(() -> new DataNotFoundException("no %d question not found,".formatted(projectCid)));
}

    public void delete(ProjectEntity projectEntity) {
        projectRepository.delete(projectEntity);
    }

    public void modify(ProjectEntity projectEntity, String title, String body) {
        projectEntity.setTitle(title);
        projectEntity.setBody(body);
        projectEntity.setUpdatedAt(LocalDateTime.now());
        projectRepository.save(projectEntity);
    }
}


package com.likelion.stepstone.projects;


import com.likelion.stepstone.Util.DataNotFoundException;
import com.likelion.stepstone.projects.model.ProjectDto;
import com.likelion.stepstone.projects.model.ProjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;


public class ProjectService {
    public final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void create(ProjectDto projectDto) {
        ProjectEntity projectEntity = ProjectEntity.toEntity(projectDto);
        projectEntity.setTitle(projectDto.getTitle());
        projectEntity.setBody(projectDto.getBody());
        projectEntity.setCreatedAt(LocalDateTime.now());



        projectRepository.save(projectEntity);
    }

    public Page<ProjectEntity> getList(int page) {
        Pageable pageable = PageRequest.of(page, 5); // 한 페이지에 10까지 가능
        return projectRepository.findAll(pageable);
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


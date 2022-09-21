package com.likelion.stepstone.projects;

import com.likelion.stepstone.Util.DataNotFoundException;
import com.likelion.stepstone.authentication.PrincipalDetails;
import com.likelion.stepstone.projects.model.ProjectDto;
import com.likelion.stepstone.projects.model.ProjectEntity;
import com.likelion.stepstone.user.model.UserDto;
import com.likelion.stepstone.user.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public class ProjectService {
    public final ProjectRepository projectRepository;
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void create(ProjectDto projectDto, PrincipalDetails principalDetails) {
        ProjectEntity projectEntity = ProjectEntity.toEntity(projectDto);
        UserEntity userEntity = principalDetails.getUser();

        projectEntity.setTitle(projectDto.getTitle());
        projectEntity.setBody(projectDto.getBody());
        projectEntity.setCreatedAt(LocalDateTime.now());
        projectEntity.setUser(userEntity);

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

public ProjectDto getProjectDto(Long projectCid) {

    ProjectEntity projectEntity =  projectRepository.findByProjectCid(projectCid)
            .orElseThrow(() -> new DataNotFoundException("no %d question not found,".formatted(projectCid)));
    ProjectDto projectDto = ProjectDto.toDto(projectEntity);

    return projectDto;
}
    @PreAuthorize("hasRole('ROLE_ADMIN') or #projectDto.user.userId == authentication.principal.username")
    @Transactional
    public void delete( ProjectDto projectDto) {
        Long projectCid = projectDto.getProjectCid();
        projectRepository.deleteByProjectCid(projectCid);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #projectDto.user.userId == authentication.principal.username")
    public void modify( ProjectDto projectDto, String title, String body) {
        Optional<ProjectEntity> projectEntities =  projectRepository.findByProjectCid(projectDto.getProjectCid());
        ProjectEntity projectEntity = projectEntities.get();

        projectEntity.setTitle(title);
        projectEntity.setBody(body);
        projectEntity.setUpdatedAt(LocalDateTime.now());
        projectRepository.save(projectEntity);
    }
}


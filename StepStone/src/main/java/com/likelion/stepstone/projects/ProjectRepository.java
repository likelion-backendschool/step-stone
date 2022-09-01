package com.likelion.stepstone.projects;


import com.likelion.stepstone.projects.model.ProjectEntity;
import com.likelion.stepstone.workspaces.model.WorkSpaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    Optional<ProjectEntity> findByProjectCid(Long projectCid);
}

package com.likelion.stepstone.projects;


import com.likelion.stepstone.projects.model.ProjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
   Optional<ProjectEntity> findByProjectCid(Long projectCid);

    void deleteByProjectCid(Long projectCid);

    Page<ProjectEntity> findAllByPostCid(Long id, Pageable pageable);

    Optional<ProjectEntity> findByWorkspaceCid(Long workspaceCid);

    List<ProjectEntity> findByUserUserCid(Long userCid);
}

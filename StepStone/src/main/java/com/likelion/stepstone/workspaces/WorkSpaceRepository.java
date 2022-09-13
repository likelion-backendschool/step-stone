package com.likelion.stepstone.workspaces;

import com.likelion.stepstone.workspaces.model.WorkSpaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WorkSpaceRepository extends JpaRepository<WorkSpaceEntity, Long> {
    Optional<WorkSpaceEntity> findByWorkspaceCid(Long workspaceCid);

    void deleteByWorkspaceCid(Long workspaceCid);
}

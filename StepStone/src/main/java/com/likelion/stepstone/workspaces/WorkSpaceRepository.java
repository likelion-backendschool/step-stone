package com.likelion.stepstone.workspaces;

import com.likelion.stepstone.workspaces.model.WorkSpaceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WorkSpaceRepository extends JpaRepository<WorkSpaceEntity, Long> {
    Optional<WorkSpaceEntity> findByWorkspaceCid(Long workspaceCid);

    Page<WorkSpaceEntity> findAllByUserUserCid(Long userCid, Pageable pageable);
    void deleteByWorkspaceCid(Long workspaceCid);

    Page<WorkSpaceEntity> findAllByPostCid(Long postCid, Pageable pageable);
}

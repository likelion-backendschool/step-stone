package com.likelion.stepstone.workspaces;

import com.likelion.stepstone.Util.DataNotFoundException;
import com.likelion.stepstone.user.model.UserDto;
import com.likelion.stepstone.user.model.UserEntity;
import com.likelion.stepstone.workspaces.model.WorkSpaceDto;
import com.likelion.stepstone.workspaces.model.WorkSpaceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

public class WorkSpaceService {
    public final WorkSpaceRepository workspaceRepository;
    public WorkSpaceService(WorkSpaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }
    public void create(WorkSpaceDto workSpaceDto, UserDto userDto) {
        WorkSpaceEntity workSpaceEntity = WorkSpaceEntity.toEntity(workSpaceDto);
        UserEntity user = UserEntity.toEntity(userDto);

        workSpaceEntity.setTitle(workSpaceDto.getTitle());
        workSpaceEntity.setBody(workSpaceDto.getBody());
        workSpaceEntity.setCreatedAt(LocalDateTime.now());
        workSpaceEntity.setUser(user);

        workspaceRepository.save(workSpaceEntity);
    }
    public Page<WorkSpaceEntity> getList(int page) {
        Pageable pageable = getPageable(page, 5, Sort.by(Sort.Direction.DESC, "workspaceCid"));
        return workspaceRepository.findAll(pageable);
    }

    private Pageable getPageable(int page, int size, Sort DESC) {
        Pageable pageable = PageRequest.of(page, size, DESC);
        return pageable;
    }

    public WorkSpaceEntity getWorkSpaceEntity(Long workspaceCid) {
        return workspaceRepository.findByWorkspaceCid(workspaceCid)
            .orElseThrow(() -> new DataNotFoundException("no %d question not found,".formatted(workspaceCid)));
    }

    public void delete(WorkSpaceEntity workSpaceEntity) {
        workspaceRepository.delete(workSpaceEntity);
    }

    public void modify(WorkSpaceEntity workSpaceEntity, String title, String body) {
        workSpaceEntity.setTitle(title);
        workSpaceEntity.setBody(body);
        workSpaceEntity.setUpdatedAt(LocalDateTime.now());
        workspaceRepository.save(workSpaceEntity);
    }

}

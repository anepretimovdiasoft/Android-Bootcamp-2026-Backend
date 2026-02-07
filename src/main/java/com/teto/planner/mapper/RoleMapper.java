package com.teto.planner.mapper;

import com.teto.planner.dto.RoleDto;
import com.teto.planner.entity.RoleEntity;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleDto toDto(RoleEntity role) {
        return new RoleDto(role.getId(), role.getSlug(), role.getName());
    }
}

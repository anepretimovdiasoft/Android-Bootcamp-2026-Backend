package com.teto.planner.service;

import com.teto.planner.dto.CreateRoleRequest;
import com.teto.planner.dto.PageMeta;
import com.teto.planner.dto.RoleDto;
import com.teto.planner.dto.RolesPage;
import com.teto.planner.dto.UpdateRoleRequest;
import com.teto.planner.entity.RoleEntity;
import com.teto.planner.exception.ConflictException;
import com.teto.planner.exception.NotFoundException;
import com.teto.planner.mapper.RoleMapper;
import com.teto.planner.pagination.Pagination;
import com.teto.planner.repository.RoleRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Transactional(readOnly = true)
    public RolesPage listRoles(int page, int size) {
        Page<RoleEntity> roles = roleRepository.findAll(
                Pagination.pageRequest(page, size, Sort.by(Sort.Order.asc("slug"), Sort.Order.asc("id")))
        );
        List<RoleDto> items = roles.getContent().stream().map(roleMapper::toDto).collect(Collectors.toList());
        return new RolesPage(items, new PageMeta(page, size, roles.getTotalElements()));
    }

    @Transactional(readOnly = true)
    public RoleDto getRole(UUID roleId) {
        return roleMapper.toDto(findRole(roleId));
    }

    @Transactional
    public RoleDto createRole(CreateRoleRequest request) {
        if (roleRepository.findBySlugIgnoreCase(request.slug()).isPresent()) {
            throw new ConflictException("ROLE_EXISTS", "Role already exists");
        }
        RoleEntity role = new RoleEntity();
        role.setId(UUID.randomUUID());
        role.setSlug(request.slug());
        role.setName(request.name());
        return roleMapper.toDto(roleRepository.save(role));
    }

    @Transactional
    public RoleDto updateRole(UUID roleId, UpdateRoleRequest request) {
        RoleEntity role = findRole(roleId);
        if (request.slug() != null && !request.slug().equalsIgnoreCase(role.getSlug())) {
            if (roleRepository.findBySlugIgnoreCase(request.slug()).isPresent()) {
                throw new ConflictException("ROLE_EXISTS", "Role already exists");
            }
            role.setSlug(request.slug());
        }
        if (request.name() != null) {
            role.setName(request.name());
        }
        return roleMapper.toDto(role);
    }

    @Transactional
    public void deleteRole(UUID roleId) {
        roleRepository.delete(findRole(roleId));
    }

    @Transactional(readOnly = true)
    public RoleEntity findRole(UUID roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("ROLE_NOT_FOUND", "Role not found"));
    }
}

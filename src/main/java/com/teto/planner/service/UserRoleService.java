package com.teto.planner.service;

import com.teto.planner.dto.RoleDto;
import com.teto.planner.entity.RoleEntity;
import com.teto.planner.entity.UserEntity;
import com.teto.planner.exception.BadRequestException;
import com.teto.planner.mapper.RoleMapper;
import com.teto.planner.repository.RoleRepository;
import com.teto.planner.repository.UserRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRoleService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public UserRoleService(UserRepository userRepository, RoleRepository roleRepository, RoleMapper roleMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Transactional(readOnly = true)
    public List<RoleDto> getRoles(UUID userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("USER_NOT_FOUND", "User not found"));
        return user.getRoles().stream().map(roleMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public List<RoleDto> replaceRoles(UUID userId, List<String> roleSlugs) {
        if (roleSlugs == null || roleSlugs.isEmpty()) {
            throw new BadRequestException("VALIDATION_ERROR", "roleSlugs is required");
        }
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("USER_NOT_FOUND", "User not found"));
        Set<RoleEntity> roles = new HashSet<>();
        for (String slug : roleSlugs) {
            RoleEntity role = roleRepository.findBySlugIgnoreCase(slug)
                    .orElseThrow(() -> new BadRequestException("ROLE_NOT_FOUND", "Role not found: " + slug));
            roles.add(role);
        }
        user.setRoles(roles);
        return user.getRoles().stream().map(roleMapper::toDto).collect(Collectors.toList());
    }
}

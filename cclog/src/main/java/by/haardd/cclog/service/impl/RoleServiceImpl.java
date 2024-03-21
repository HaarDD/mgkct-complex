package by.haardd.cclog.service.impl;


import by.haardd.cclog.dto.RoleDto;
import by.haardd.cclog.exception.types.extended.ResourceNotFoundException;
import by.haardd.cclog.mapper.RoleMapper;
import by.haardd.cclog.repository.RoleRepository;
import by.haardd.cclog.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public List<RoleDto> getAll() {
        return roleRepository.findAll().stream().map(roleMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public RoleDto getById(Long id) {
        return roleRepository.findById(id).map(roleMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Role was not found", id.toString()));
    }

    @Override
    @Transactional
    public RoleDto save(RoleDto roleDto) {
        return roleMapper.toDto(roleRepository.save(roleMapper.toEntity(roleDto)));
    }

    @Override
    @Transactional
    public RoleDto update(RoleDto roleDto, Long id) {
        return roleMapper.toDto(roleRepository
                .save(roleMapper.partialUpdate(roleDto, roleRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Role was not found", id.toString())))));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        roleRepository.delete(roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role was not found", id.toString())));
    }
}

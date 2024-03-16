package by.haardd.cclog.service;

import by.haardd.cclog.dto.RoleDto;

import java.util.List;

public interface RoleService {

    List<RoleDto> getAll();

    RoleDto getById(Long id);

    RoleDto save(RoleDto roleDto);

    RoleDto update(RoleDto roleDto, Long id);

    void delete(Long id);

}

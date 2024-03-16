package by.haardd.cclog.service;

import by.haardd.cclog.dto.UserStatusDto;

import java.util.List;

public interface UserStatusService {
    List<UserStatusDto> getAll();

    UserStatusDto getById(Long id);

    UserStatusDto save(UserStatusDto userStatusDto);

    UserStatusDto update(UserStatusDto userStatusDto, Long id);

    void delete(Long id);
}

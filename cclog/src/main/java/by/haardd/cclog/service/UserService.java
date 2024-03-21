package by.haardd.cclog.service;

import by.haardd.cclog.dto.RegisterUserDto;
import by.haardd.cclog.dto.UserDto;
import by.haardd.cclog.entity.enums.RoleNameEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

public interface UserService {

    List<UserDto> getAllByPageable(Pageable pageable);

    UserDto getById(Long id);

    UserDto getByLogin(String login);

    String getRefreshTokenByLogin(String login);

    Timestamp getRefreshTokenExpirationDateByLogin(String login);

    UserDto save(RegisterUserDto registerUserDto, RoleNameEnum roleName);

    UserDto saveWithAdminKey(RegisterUserDto registerUserDto, String registrationAdminKey);

    UserDto saveWithUserKey(RegisterUserDto registerUserDto, String registrationAdminKey);

    void clearRefreshTokenByLogin(String login);

    UserDto update(RegisterUserDto registerUserDto, Long id);

    void delete(Long id);

    void updateRefreshTokenByLogin(String login, String newRefreshToken, Timestamp newTokenExpirationDate);

    boolean isUserHasPermission(Long id);
}

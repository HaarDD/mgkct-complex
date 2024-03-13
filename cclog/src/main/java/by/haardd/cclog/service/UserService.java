package by.haardd.cclog.service;

import by.haardd.cclog.dto.RegisterUserDto;
import by.haardd.cclog.dto.UserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.List;

public interface UserService {

    List<UserDto> getAllByPageable(Pageable pageable);

    UserDto getById(Long id);

    UserDto save(RegisterUserDto registerUserDto);

    UserDto saveWithAdminKey(RegisterUserDto registerUserDto, String registrationAdminKey);

    String updateAndGetRefreshTokenByAuth(Authentication authentication, Duration expiration);

    String updateAndGetRefreshTokenByRefresh(String refreshToken, Duration expiration);

    void clearRefreshToken(Authentication authentication);

    UserDto update(RegisterUserDto registerUserDto, Long id);

    void delete(Long id);
}

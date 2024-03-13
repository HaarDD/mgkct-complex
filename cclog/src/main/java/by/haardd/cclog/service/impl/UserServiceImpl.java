package by.haardd.cclog.service.impl;

import by.haardd.cclog.dto.RegisterUserDto;
import by.haardd.cclog.dto.UserDto;
import by.haardd.cclog.entity.User;
import by.haardd.cclog.entity.enums.RoleNameEnum;
import by.haardd.cclog.entity.enums.UserStatusEnum;
import by.haardd.cclog.exception.types.ResourceNotFoundException;
import by.haardd.cclog.exception.types.TokenExpiredException;
import by.haardd.cclog.exception.types.WrongRegistrationCodeException;
import by.haardd.cclog.mapper.UserMapper;
import by.haardd.cclog.repository.RoleRepository;
import by.haardd.cclog.repository.UserRepository;
import by.haardd.cclog.repository.UserStatusRepository;
import by.haardd.cclog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final String registrationAdminKey;

    public UserServiceImpl(RoleRepository roleRepository,
                           UserRepository userRepository,
                           UserStatusRepository userStatusRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder,
                           @Value("${register.registration-admin-key}") String registrationAdminKey) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.registrationAdminKey = registrationAdminKey;
        this.userStatusRepository = userStatusRepository;
    }

    @Override
    public List<UserDto> getAllByPageable(Pageable pageable) {
        return userRepository.findAll(pageable).stream().map(userMapper::toDto).toList();
    }

    @Override
    public UserDto getById(Long id) {
        return userMapper.toDto(userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User was not found", id.toString())));
    }

    @Override
    @Transactional
    public UserDto save(RegisterUserDto registerUserDto) {
        return null;
    }

    @Override
    @Transactional
    public UserDto saveWithAdminKey(RegisterUserDto registerUserDto, String registrationAdminKey) {
        if (!registrationAdminKey.equals(this.registrationAdminKey)) {
            throw new WrongRegistrationCodeException("Registration code was wrong!");
        }

        User user = userMapper.toEntity(registerUserDto, passwordEncoder);

        user.setRole(roleRepository.findByName(RoleNameEnum.ROLE_ENGINEER)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found", RoleNameEnum.ROLE_ENGINEER.name())));
        user.setUserStatus(userStatusRepository.findByName(UserStatusEnum.OK)
                .orElseThrow(() -> new ResourceNotFoundException("User status not found", UserStatusEnum.OK.name())));

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public String updateAndGetRefreshTokenByAuth(Authentication authentication, Duration expiration) {
        String login = authentication.getName();
        User user = userRepository.findByLogin(login).orElseThrow(() -> new ResourceNotFoundException("User was not found by login", login));
        String refreshToken = UUID.randomUUID().toString();
        user.setRefreshToken(refreshToken);
        user.setRefreshTokenExpiration(Timestamp.from(Instant.now().plus(expiration)));
        userRepository.save(user);
        return refreshToken;
    }

    @Override
    @Transactional
    public String updateAndGetRefreshTokenByRefresh(String refreshToken, Duration expiration) {
        User user = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new ResourceNotFoundException("User was not found by refresh", refreshToken));

        Timestamp currentTimestamp = Timestamp.from(Instant.now());
        if (user.getRefreshTokenExpiration().compareTo(currentTimestamp) > 0) {
            String newRefreshToken = UUID.randomUUID().toString();
            user.setRefreshToken(newRefreshToken);
            user.setRefreshTokenExpiration(Timestamp.from(currentTimestamp.toInstant().plus(expiration)));
        } else {
            user.setRefreshToken(null);
            user.setRefreshTokenExpiration(null);
            throw new TokenExpiredException("Token was expired!");
        }

        userRepository.save(user);
        return user.getRefreshToken();
    }

    @Override
    @Transactional
    public void clearRefreshToken(Authentication authentication) {
        String login = authentication.getName();
        User user = userRepository.findByLogin(login).orElseThrow(() -> {
            log.info("User was not found by login: {}", login);
            return new ResourceNotFoundException("User was not found by login", login);
        });
        user.setRefreshToken(null);
        user.setRefreshTokenExpiration(null);
        userRepository.save(user);
    }


    @Override
    @Transactional
    public UserDto update(RegisterUserDto registerUserDto, Long id) {
        return null;
    }

    @Override
    @Transactional
    public void delete(Long id) {

    }


}

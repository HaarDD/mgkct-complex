package by.haardd.cclog.service.impl;

import by.haardd.cclog.dto.RegisterUserDto;
import by.haardd.cclog.dto.UserDto;
import by.haardd.cclog.entity.User;
import by.haardd.cclog.entity.enums.RoleNameEnum;
import by.haardd.cclog.entity.enums.UserStatusEnum;
import by.haardd.cclog.exception.types.extended.ResourceNotFoundException;
import by.haardd.cclog.exception.types.extended.TokenInvalidException;
import by.haardd.cclog.exception.types.extended.UserAlreadyExistsException;
import by.haardd.cclog.exception.types.extended.WrongRegistrationCodeException;
import by.haardd.cclog.mapper.user.UserMapper;
import by.haardd.cclog.repository.RoleRepository;
import by.haardd.cclog.repository.UserRepository;
import by.haardd.cclog.repository.UserStatusRepository;
import by.haardd.cclog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

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
    private final String registrationUserKey;

    public UserServiceImpl(RoleRepository roleRepository,
                           UserRepository userRepository,
                           UserStatusRepository userStatusRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder,
                           @Value("${register.registration-admin-key}") String registrationAdminKey,
                           @Value("${register.registration-user-key}") String registrationUserKey) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.registrationAdminKey = registrationAdminKey;
        this.userStatusRepository = userStatusRepository;
        this.registrationUserKey = registrationUserKey;
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
    public UserDto getByRefreshToken(String refreshToken) {
        return userMapper.toDto(userRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new TokenInvalidException("Token was invalid!")));
    }

    @Override
    public Timestamp getRefreshTokenExpirationDateByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> new ResourceNotFoundException("User was not found", login)).getRefreshTokenExpiration();
    }

    @Override
    @Transactional
    public UserDto save(RegisterUserDto registerUserDto) {
        return null;
    }

    @Override
    @Transactional
    public UserDto saveWithAdminKey(RegisterUserDto registerUserDto, String registrationAdminKey) {
        validateRegistrationKey(registrationAdminKey, this.registrationAdminKey);
        return saveUser(registerUserDto, RoleNameEnum.ROLE_ENGINEER);
    }

    @Override
    @Transactional
    public UserDto saveWithUserKey(RegisterUserDto registerUserDto, String registrationUserKey) {
        validateRegistrationKey(registrationUserKey, this.registrationUserKey);
        return saveUser(registerUserDto, RoleNameEnum.ROLE_EMPLOYEE);
    }

    private void validateRegistrationKey(String providedKey, String expectedKey) {
        if (!providedKey.equals(expectedKey)) {
            throw new WrongRegistrationCodeException("Registration code was wrong!");
        }
    }

    private UserDto saveUser(RegisterUserDto registerUserDto, RoleNameEnum roleName) {
        if (userRepository.existsByLogin(registerUserDto.getLogin())) {
            throw new UserAlreadyExistsException("User with login '%s' already exists!".formatted(registerUserDto.getLogin()));
        }

        User user = userMapper.toEntity(registerUserDto, passwordEncoder);
        user.setRole(roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found", roleName.name())));
        user.setUserStatus(userStatusRepository.findByName(UserStatusEnum.OK)
                .orElseThrow(() -> new ResourceNotFoundException("User status not found", UserStatusEnum.OK.name())));

        return userMapper.toDto(userRepository.save(user));
    }


    @Override
    @Transactional
    public void updateRefreshTokenByLogin(String login, String newRefreshToken, Timestamp newTokenExpirationDate) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException("User was not found by login", login));
        user.setRefreshToken(newRefreshToken);
        user.setRefreshTokenExpiration(newTokenExpirationDate);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void clearRefreshTokenByLogin(String login) {
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
        return null; //TODO
    }

    @Override
    @Transactional
    public void delete(Long id) {
        //TODO
    }


}

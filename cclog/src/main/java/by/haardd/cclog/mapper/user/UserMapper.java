package by.haardd.cclog.mapper.user;

import by.haardd.cclog.dto.RegisterUserDto;
import by.haardd.cclog.dto.UserDto;
import by.haardd.cclog.entity.User;
import by.haardd.cclog.mapper.request.RequestMapperWithoutUser;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {RequestMapperWithoutUser.class})
public interface UserMapper {
    User toEntity(RegisterUserDto userDto, @Context PasswordEncoder passwordEncoder);

    @AfterMapping
    default void linkRequests(@MappingTarget User user) {
        user.getRequests().forEach(request -> request.setCreatedByUser(user));
    }

    @AfterMapping
    default void postConstruct(RegisterUserDto registerUserDto, @MappingTarget User user, @Context PasswordEncoder passwordEncoder) {
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
    }

    UserDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(RegisterUserDto userDto, @MappingTarget User user, @Context PasswordEncoder passwordEncoder);
}
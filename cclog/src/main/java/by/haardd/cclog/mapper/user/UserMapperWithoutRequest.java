package by.haardd.cclog.mapper.user;

import by.haardd.cclog.dto.RegisterUserDto;
import by.haardd.cclog.dto.UserDto;
import by.haardd.cclog.entity.User;
import by.haardd.cclog.mapper.request.RequestMapperWithoutUser;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {RequestMapperWithoutUser.class})
public interface UserMapperWithoutRequest {
    @Mapping(target = "requests", ignore = true)
    User toEntity(RegisterUserDto userDto, @Context PasswordEncoder passwordEncoder);

    @AfterMapping
    @Mapping(target = "requests", ignore = true)
    default void linkRequests(@MappingTarget User user) {
        user.getRequests().forEach(request -> request.setCreatedByUser(user));
    }

    @AfterMapping
    @Mapping(target = "requests", ignore = true)
    default void postConstruct(RegisterUserDto registerUserDto, @MappingTarget User user, @Context PasswordEncoder passwordEncoder) {
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
    }

    @Mapping(target = "requests", ignore = true)
    UserDto toDto(User user);

    @Mapping(target = "requests", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(RegisterUserDto userDto, @MappingTarget User user);
}
package by.haardd.cclog.mapper;

import by.haardd.cclog.dto.RoleDto;
import by.haardd.cclog.entity.Role;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RoleMapper {
    Role toEntity(RoleDto roleDto);

    @AfterMapping
    default void linkUsers(@MappingTarget Role role) {
        role.getUsers().forEach(user -> user.setRole(role));
    }

    RoleDto toDto(Role role);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Role partialUpdate(RoleDto roleDto, @MappingTarget Role role);
}
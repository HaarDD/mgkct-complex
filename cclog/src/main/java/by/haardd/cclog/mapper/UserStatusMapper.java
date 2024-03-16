package by.haardd.cclog.mapper;

import by.haardd.cclog.dto.UserStatusDto;
import by.haardd.cclog.entity.UserStatus;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserStatusMapper {
    UserStatus toEntity(UserStatusDto userStatusDto);

    UserStatusDto toDto(UserStatus userStatus);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserStatus partialUpdate(UserStatusDto userStatusDto, @MappingTarget UserStatus userStatus);
}
package by.haardd.cclog.mapper.request;

import by.haardd.cclog.dto.RequestDto;
import by.haardd.cclog.entity.Request;
import by.haardd.cclog.mapper.user.UserMapperWithoutRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = UserMapperWithoutRequest.class)
public interface RequestMapperWithoutPriority {
    @Mapping(target = "priority", ignore = true)
    Request toEntity(RequestDto requestDto);

    @Mapping(target = "priority", ignore = true)
    RequestDto toDto(Request request);

    @Mapping(target = "priority", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Request partialUpdate(RequestDto requestDto, @MappingTarget Request request);
}
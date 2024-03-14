package by.haardd.cclog.mapper.request;

import by.haardd.cclog.dto.RequestDto;
import by.haardd.cclog.entity.Request;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RequestMapperWithoutUser {
    @Mapping(target = "createdByUser", ignore = true)
    Request toEntity(RequestDto requestDto);

    @Mapping(target = "createdByUser", ignore = true)
    RequestDto toDto(Request request);

    @Mapping(target = "createdByUser", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Request partialUpdate(RequestDto requestDto, @MappingTarget Request request);
}
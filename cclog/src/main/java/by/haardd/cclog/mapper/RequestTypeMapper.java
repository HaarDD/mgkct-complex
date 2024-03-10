package by.haardd.cclog.mapper;

import by.haardd.cclog.dto.RequestTypeDto;
import by.haardd.cclog.entity.RequestType;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RequestTypeMapper {
    RequestType toEntity(RequestTypeDto requestTypeDto);

    @AfterMapping
    default void linkRequests(@MappingTarget RequestType requestType) {
        requestType.getRequests().forEach(request -> request.setRequestType(requestType));
    }

    RequestTypeDto toDto(RequestType requestType);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    RequestType partialUpdate(RequestTypeDto requestTypeDto, @MappingTarget RequestType requestType);
}
package by.haardd.cclog.mapper;

import by.haardd.cclog.dto.StatusDto;
import by.haardd.cclog.entity.Status;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface StatusMapper {
    Status toEntity(StatusDto statusDto);

    @AfterMapping
    default void linkRequests(@MappingTarget Status status) {
        status.getRequests().forEach(request -> request.setStatus(status));
    }

    StatusDto toDto(Status status);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Status partialUpdate(StatusDto statusDto, @MappingTarget Status status);
}
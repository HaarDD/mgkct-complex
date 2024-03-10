package by.haardd.cclog.mapper;

import by.haardd.cclog.dto.PriorityDto;
import by.haardd.cclog.entity.Priority;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PriorityMapper {
    Priority toEntity(PriorityDto priorityDto);

    @AfterMapping
    default void linkRequests(@MappingTarget Priority priority) {
        priority.getRequests().forEach(request -> request.setPriority(priority));
    }

    @AfterMapping
    default void linkRoles(@MappingTarget Priority priority) {
        priority.getRoles().forEach(role -> role.setPriority(priority));
    }

    PriorityDto toDto(Priority priority);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Priority partialUpdate(PriorityDto priorityDto, @MappingTarget Priority priority);
}
package by.haardd.cclog.mapper.proiroty;

import by.haardd.cclog.dto.PriorityDto;
import by.haardd.cclog.entity.Priority;
import by.haardd.cclog.mapper.request.RequestMapperWithoutPriority;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = RequestMapperWithoutPriority.class)
public interface PriorityMapperWithoutRequest {

    @Mapping(target = "requests", ignore = true)
    Priority toEntity(PriorityDto priorityDto);

    @AfterMapping
    default void linkRoles(@MappingTarget Priority priority) {
        priority.getRoles().forEach(role -> role.setPriority(priority));
    }
    @Mapping(target = "requests", ignore = true)
    PriorityDto toDto(Priority priority);
    @Mapping(target = "requests", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Priority partialUpdate(PriorityDto priorityDto, @MappingTarget Priority priority);
}
package by.haardd.cclog.mapper;

import by.haardd.cclog.dto.RequestFieldTypeDto;
import by.haardd.cclog.entity.RequestFieldType;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RequestFieldTypeMapper {
    RequestFieldType toEntity(RequestFieldTypeDto requestFieldTypeDto);

    RequestFieldTypeDto toDto(RequestFieldType requestFieldType);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    RequestFieldType partialUpdate(RequestFieldTypeDto requestFieldTypeDto, @MappingTarget RequestFieldType requestFieldType);
}
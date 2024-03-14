package by.haardd.cclog.mapper.request;

import by.haardd.cclog.dto.RequestDto;
import by.haardd.cclog.entity.Request;
import by.haardd.cclog.mapper.proiroty.PriorityMapperWithoutRequest;
import by.haardd.cclog.mapper.user.UserMapperWithoutRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapperWithoutRequest.class, PriorityMapperWithoutRequest.class})
public interface RequestMapper {
    Request toEntity(RequestDto requestDto);

    RequestDto toDto(Request request);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Request partialUpdate(RequestDto requestDto, @MappingTarget Request request);
}
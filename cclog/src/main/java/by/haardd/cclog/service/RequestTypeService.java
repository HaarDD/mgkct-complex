package by.haardd.cclog.service;

import by.haardd.cclog.dto.RequestTypeDto;

import java.util.List;

public interface RequestTypeService {

    List<RequestTypeDto> getAll();

    RequestTypeDto getById(Long id);

    RequestTypeDto save(RequestTypeDto requestTypeDto);

    RequestTypeDto update(RequestTypeDto requestTypeDto, Long id);

    void delete(Long id);

}

package by.haardd.cclog.service;

import by.haardd.cclog.dto.PriorityDto;

import java.util.List;

public interface PriorityService {

    List<PriorityDto> getAll();

    PriorityDto getById(Long id);

    PriorityDto save(PriorityDto priorityDto);

    PriorityDto update(PriorityDto priorityDto, Long id);

    void delete(Long id);

}

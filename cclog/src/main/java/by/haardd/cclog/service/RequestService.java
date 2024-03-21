package by.haardd.cclog.service;

import by.haardd.cclog.dto.PriorityDto;
import by.haardd.cclog.dto.RequestDto;
import by.haardd.cclog.dto.StatusDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RequestService {

    Long getTotalCount();

    List<RequestDto> getAllByPageable(Pageable pageable);

    RequestDto getById(Long id);

    RequestDto save(RequestDto requestDto);

    RequestDto update(RequestDto requestDto, Long id);

    void delete(Long id);

    void addEngineerComment(String text, Long id);

    void setStatus(StatusDto statusDto, Long id);

    void setPriority(PriorityDto priorityDto, Long id);

    boolean isUserHasPermission(Long id);
}

package by.haardd.cclog.service;

import by.haardd.cclog.dto.RequestDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RequestService {

    List<RequestDto> getAllByPageable(Pageable pageable);

    RequestDto getById(Long id);

    RequestDto save(RequestDto requestDto);

    RequestDto update(RequestDto requestDto, Long id);

    void delete(Long id);

    boolean isUserHasPermission(Long id);
}

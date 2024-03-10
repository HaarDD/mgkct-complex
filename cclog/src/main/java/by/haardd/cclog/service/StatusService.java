package by.haardd.cclog.service;

import by.haardd.cclog.dto.StatusDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StatusService {

    List<StatusDto> getAllByPageable(Pageable pageable);

    StatusDto getById(Long id);

    StatusDto save(StatusDto statusDto);

    StatusDto update(StatusDto statusDto, Long id);

    void delete(Long id);

}

package by.haardd.cclog.service.impl;

import by.haardd.cclog.dto.StatusDto;
import by.haardd.cclog.exception.types.extended.ResourceNotFoundException;
import by.haardd.cclog.mapper.StatusMapper;
import by.haardd.cclog.repository.StatusRepository;
import by.haardd.cclog.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;
    private final StatusMapper statusMapper;

    @Override
    public List<StatusDto> getAllByPageable(Pageable pageable) {
        return statusRepository.findAll(pageable).stream().map(statusMapper::toDto).toList();
    }

    @Override
    public StatusDto getById(Long id) {
        return statusMapper.toDto(statusRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Status was not found", id.toString())));
    }

    @Override
    @Transactional
    public StatusDto save(StatusDto statusDto) {
        return null;
    }

    @Override
    @Transactional
    public StatusDto update(StatusDto statusDto, Long id) {
        return null;
    }

    @Override
    @Transactional
    public void delete(Long id) {

    }
}

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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;
    private final StatusMapper statusMapper;

    @Override
    public List<StatusDto> getAll() {
        return statusRepository.findAll().stream().map(statusMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public StatusDto getById(Long id) {
        return statusMapper.toDto(statusRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Status was not found", id.toString())));
    }

    @Override
    @Transactional
    public StatusDto save(StatusDto statusDto) {
        return statusMapper.toDto(statusRepository.save(statusMapper.toEntity(statusDto)));
    }

    @Override
    @Transactional
    public StatusDto update(StatusDto statusDto, Long id) {
        return statusMapper.toDto(statusRepository
                .save(statusMapper.partialUpdate(statusDto, statusRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Status was not found", id.toString())))));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        statusRepository.delete(statusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Status was not found", id.toString())));
    }
}

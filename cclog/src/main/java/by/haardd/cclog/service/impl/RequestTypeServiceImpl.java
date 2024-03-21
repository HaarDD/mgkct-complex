package by.haardd.cclog.service.impl;

import by.haardd.cclog.dto.RequestTypeDto;
import by.haardd.cclog.exception.types.extended.ResourceNotFoundException;
import by.haardd.cclog.mapper.RequestTypeMapper;
import by.haardd.cclog.repository.RequestTypeRepository;
import by.haardd.cclog.service.RequestTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestTypeServiceImpl implements RequestTypeService {
    private final RequestTypeMapper requestTypeMapper;


    private final RequestTypeRepository requestTypeRepository;

    @Override
    public List<RequestTypeDto> getAll() {
        return requestTypeRepository.findAll().stream().map(requestTypeMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public RequestTypeDto getById(Long id) {
        return requestTypeRepository.findById(id).map(requestTypeMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Request type was not found", id.toString()));
    }

    @Override
    @Transactional
    public RequestTypeDto save(RequestTypeDto requestTypeDto) {
        return requestTypeMapper.toDto(requestTypeRepository.save(requestTypeMapper.toEntity(requestTypeDto)));
    }

    @Override
    @Transactional
    public RequestTypeDto update(RequestTypeDto requestTypeDto, Long id) {
        return requestTypeMapper.toDto(requestTypeMapper.partialUpdate(requestTypeDto,
                requestTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Request type was not found", id.toString()))));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        requestTypeRepository.delete(requestTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Request type was not found", id.toString())));
    }
}

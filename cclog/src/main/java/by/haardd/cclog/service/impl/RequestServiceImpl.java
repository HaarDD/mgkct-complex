package by.haardd.cclog.service.impl;

import by.haardd.cclog.dto.RequestDto;
import by.haardd.cclog.exception.types.ResourceNotFoundException;
import by.haardd.cclog.mapper.RequestMapper;
import by.haardd.cclog.repository.RequestRepository;
import by.haardd.cclog.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;

    @Override
    public List<RequestDto> getAllByPageable(Pageable pageable) {
        return requestRepository.findAll(pageable).stream().map(requestMapper::toDto).toList();
    }

    @Override
    public RequestDto getById(Long id) {
        return requestMapper.toDto(requestRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Request was not found", id.toString())));
    }

    @Override
    @Transactional
    public RequestDto save(RequestDto requestDto) {

        requestRepository.save(requestMapper.toEntity(requestDto));

        return null;
    }

    @Override
    @Transactional
    public RequestDto update(RequestDto requestDto, Long id) {
        return null;
    }

    @Override
    @Transactional
    public void delete(Long id) {

    }
}

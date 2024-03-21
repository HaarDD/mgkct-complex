package by.haardd.cclog.service.impl;

import by.haardd.cclog.dto.PriorityDto;
import by.haardd.cclog.exception.types.extended.ResourceNotFoundException;
import by.haardd.cclog.mapper.priority.PriorityMapper;
import by.haardd.cclog.repository.PriorityRepository;
import by.haardd.cclog.service.PriorityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PriorityServiceImpl implements PriorityService {

    private final PriorityRepository priorityRepository;
    private final PriorityMapper priorityMapper;

    @Override
    public List<PriorityDto> getAll() {
        return priorityRepository.findAll().stream().map(priorityMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public PriorityDto getById(Long id) {
        return priorityRepository.findById(id).map(priorityMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Priority was not found", id.toString()));
    }

    @Override
    @Transactional
    public PriorityDto save(PriorityDto priorityDto) {
        return priorityMapper.toDto(priorityRepository.save(priorityMapper.toEntity(priorityDto)));
    }

    @Override
    @Transactional
    public PriorityDto update(PriorityDto priorityDto, Long id) {
        return priorityMapper.toDto(priorityRepository.save(priorityMapper.partialUpdate(priorityDto,
                priorityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Priority was not found", id.toString())))));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        priorityRepository.delete(priorityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Priority was not found", id.toString())));
    }
}

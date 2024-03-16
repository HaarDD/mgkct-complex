package by.haardd.cclog.service.impl;

import by.haardd.cclog.dto.PriorityDto;
import by.haardd.cclog.dto.RequestDto;
import by.haardd.cclog.dto.StatusDto;
import by.haardd.cclog.entity.Request;
import by.haardd.cclog.entity.User;
import by.haardd.cclog.entity.enums.StatusEnum;
import by.haardd.cclog.exception.types.extended.BackendResourceNotFoundException;
import by.haardd.cclog.exception.types.extended.ResourceNotFoundException;
import by.haardd.cclog.mapper.request.RequestMapper;
import by.haardd.cclog.repository.PriorityRepository;
import by.haardd.cclog.repository.RequestRepository;
import by.haardd.cclog.repository.StatusRepository;
import by.haardd.cclog.repository.UserRepository;
import by.haardd.cclog.service.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class RequestServiceImpl implements RequestService {

    private final PriorityRepository priorityRepository;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
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
        Request request = requestMapper.toEntity(requestDto);

        UserDetails authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findByLogin(authenticatedUser.getUsername())
                .orElseThrow(() -> new BackendResourceNotFoundException("Current authenticated user was not found!", authenticatedUser.getUsername()));

        request.setCreatedByUser(user);

        request.setPriority(user.getRole().getPriority());

        request.setStatus(statusRepository.findByName(StatusEnum.PENDING)
                .orElseThrow(() -> new BackendResourceNotFoundException("Default status was not found!", StatusEnum.PENDING.name())));

        return requestMapper.toDto(requestRepository.save(request));
    }

    @Override
    @Transactional
    public RequestDto update(RequestDto requestDto, Long id) {
        return requestMapper.toDto(requestRepository.save(requestMapper.partialUpdate(requestDto, requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request was not found!", id.toString())))));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        requestRepository.delete(requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request was not found!", id.toString())));
    }

    @Override
    @Transactional
    public void addEngineerComment(String text, Long id) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request was not found!", id.toString()));
        request.setEngineerComment(text);
        requestRepository.save(request);
    }

    @Override
    @Transactional
    public void setStatus(StatusDto statusDto, Long id) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request was not found!", id.toString()));
        request.setStatus(statusRepository.findById(statusDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Status was not found!", id.toString())));
        requestRepository.save(request);
    }

    @Override
    @Transactional
    public void setPriority(PriorityDto priorityDto, Long id){
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request was not found!", id.toString()));
        request.setPriority(priorityRepository.findById(priorityDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Priority was not found!", id.toString())));
        requestRepository.save(request);
    }

    @Override
    public boolean isUserHasPermission(Long id) {
        Long specifiedId = requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request was not found!", id.toString()))
                .getCreatedByUser().getId();
        UserDetails authenticatedUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentAuthenticatedLogin = authenticatedUserDetails.getUsername();
        User authenticatedUser =  userRepository.findByLogin(currentAuthenticatedLogin)
                .orElseThrow(() -> new BackendResourceNotFoundException("User was not found!", currentAuthenticatedLogin));
        return authenticatedUser.getId().equals(specifiedId);
    }

}

package by.haardd.cclog.service.impl;

import by.haardd.cclog.dto.RequestDto;
import by.haardd.cclog.entity.Request;
import by.haardd.cclog.entity.User;
import by.haardd.cclog.entity.enums.StatusEnum;
import by.haardd.cclog.exception.types.extended.BackendResourceNotFoundException;
import by.haardd.cclog.exception.types.extended.ResourceNotFoundException;
import by.haardd.cclog.mapper.request.RequestMapper;
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
        log.info("SAVE REQUEST: {}", requestDto);
        Request request = requestMapper.toEntity(requestDto);

        log.info("USER: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());

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
        requestRepository.save(requestMapper.partialUpdate(requestDto, requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request was not found!", id.toString()))));
        return requestMapper.toDto(requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request was not found! (error during save process)", id.toString())));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        requestRepository.delete(requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request was not found!", id.toString())));
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

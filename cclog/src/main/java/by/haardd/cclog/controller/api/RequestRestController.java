package by.haardd.cclog.controller.api;


import by.haardd.cclog.config.pageable.OffsetLimitPageable;
import by.haardd.cclog.config.pageable.PageableUtils;
import by.haardd.cclog.dto.RequestDto;
import by.haardd.cclog.service.RequestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/requests")
@Tag(name = "Requests", description = "Request control")
public class RequestRestController {

    private final RequestService requestService;

    @GetMapping
    public List<RequestDto> getAll(@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "0") Long offset) {
        return requestService.getAllByPageable(OffsetLimitPageable.of(PageableUtils.calcOffset(offset), PageableUtils.calcLimit(limit), Sort.by(Sort.Direction.ASC, "id")));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto create(@Valid @RequestBody RequestDto requestDto){
        return requestService.save(requestDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_ENGINEER') or @requestServiceImpl.isUserHasPermission(#id)")
    public RequestDto update(@Valid @RequestBody RequestDto requestDto, @PathVariable Long id){
        return requestService.update(requestDto,id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_ENGINEER') or @requestServiceImpl.isUserHasPermission(#id)")
    public void delete(@PathVariable Long id){
        requestService.delete(id);
    }


}

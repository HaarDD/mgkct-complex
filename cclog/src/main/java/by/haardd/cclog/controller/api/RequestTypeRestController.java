package by.haardd.cclog.controller.api;

import by.haardd.cclog.dto.RequestTypeDto;
import by.haardd.cclog.service.RequestTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/request_types")
@Tag(name = "Request type", description = "Request types control")
public class RequestTypeRestController {

    private final RequestTypeService requestTypeService;

    @GetMapping
    public List<RequestTypeDto> getAll() {
        return requestTypeService.getAll();
    }

    @GetMapping("/{id}")
    public RequestTypeDto getById(@PathVariable Long id) {
        return requestTypeService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_ENGINEER')")
    public RequestTypeDto create(@Valid @RequestBody RequestTypeDto requestDto) {
        return requestTypeService.save(requestDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_ENGINEER')")
    public RequestTypeDto update(@Valid @RequestBody RequestTypeDto requestDto, @PathVariable Long id) {
        return requestTypeService.update(requestDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_ENGINEER')")
    public void delete(@PathVariable Long id) {
        requestTypeService.delete(id);
    }


}

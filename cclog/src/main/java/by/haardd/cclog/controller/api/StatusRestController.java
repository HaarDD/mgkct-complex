package by.haardd.cclog.controller.api;

import by.haardd.cclog.dto.StatusDto;
import by.haardd.cclog.service.StatusService;
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

@RestController
@RequestMapping("/api/statuses")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Statuses", description = "Status control")
public class StatusRestController {

    private final StatusService statusService;

    @GetMapping
    private List<StatusDto> getAll(){
        return statusService.getAll();
    }

    @GetMapping("/{id}")
    public StatusDto getById(@PathVariable Long id) {
        return statusService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_ENGINEER')")
    public StatusDto create(@Valid @RequestBody StatusDto requestDto) {
        return statusService.save(requestDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_ENGINEER')")
    public StatusDto update(@Valid @RequestBody StatusDto requestDto, @PathVariable Long id) {
        return statusService.update(requestDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_ENGINEER')")
    public void delete(@PathVariable Long id) {
        statusService.delete(id);
    }

}

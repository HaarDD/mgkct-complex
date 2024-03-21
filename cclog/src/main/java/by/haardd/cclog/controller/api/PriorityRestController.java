package by.haardd.cclog.controller.api;

import by.haardd.cclog.dto.PriorityDto;
import by.haardd.cclog.service.PriorityService;
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
@RequestMapping("/api/priorities")
@Tag(name = "Priority", description = "Priority control")
public class PriorityRestController {

    private final PriorityService priorityService;

    @GetMapping
    public List<PriorityDto> getAll() {
        return priorityService.getAll();
    }

    @GetMapping("/{id}")
    public PriorityDto getById(@PathVariable Long id) {
        return priorityService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_ENGINEER')")
    public PriorityDto create(@Valid @RequestBody PriorityDto requestDto) {
        return priorityService.save(requestDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_ENGINEER')")
    public PriorityDto update(@Valid @RequestBody PriorityDto requestDto, @PathVariable Long id) {
        return priorityService.update(requestDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_ENGINEER')")
    public void delete(@PathVariable Long id) {
        priorityService.delete(id);
    }


}

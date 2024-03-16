package by.haardd.cclog.controller.api;

import by.haardd.cclog.config.pageable.OffsetLimitPageable;
import by.haardd.cclog.dto.StatusDto;
import by.haardd.cclog.service.StatusService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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





}

package by.haardd.cclog.controller.api;


import by.haardd.cclog.config.pageable.OffsetLimitPageable;
import by.haardd.cclog.config.pageable.PageableUtils;
import by.haardd.cclog.dto.RequestDto;
import by.haardd.cclog.service.RequestService;
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
@RequestMapping("/api/requests")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Requests", description = "Request control")
public class RequestRestController {


    private final RequestService requestService;

    @GetMapping
    private List<RequestDto> getAll(@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "0") Long offset) {
        return requestService.getAllByPageable(OffsetLimitPageable.of(PageableUtils.calcOffset(offset), PageableUtils.calcLimit(limit), Sort.by(Sort.Direction.ASC, "id")));
    }


}

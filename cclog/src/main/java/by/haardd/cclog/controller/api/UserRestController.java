package by.haardd.cclog.controller.api;


import by.haardd.cclog.config.pageable.OffsetLimitPageable;
import by.haardd.cclog.config.pageable.PageableUtils;
import by.haardd.cclog.dto.RegisterUserDto;
import by.haardd.cclog.dto.RequestDto;
import by.haardd.cclog.dto.UserDto;
import by.haardd.cclog.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/users")
@Tag(name = "User data", description = "User data control")
public class UserRestController {


    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ENGINEER')")
    public List<UserDto> getAll(@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "0") Long offset) {
        return userService.getAllByPageable(OffsetLimitPageable.of(PageableUtils.calcOffset(offset), PageableUtils.calcLimit(limit), Sort.by(Sort.Direction.ASC, "id")));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_ENGINEER') or @userServiceImpl.isUserHasPermission(#id)")
    public UserDto update(@Valid @RequestBody RegisterUserDto registerUserDto, @PathVariable Long id){
        return userService.update(registerUserDto, id);
    }

}

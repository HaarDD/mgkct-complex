package by.haardd.cclog.dto;

import by.haardd.cclog.entity.Priority;
import by.haardd.cclog.entity.User;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Value
public class RoleDto implements Serializable {

    @NotNull
    @Size(max = 50)
    String name;

    PriorityDto priority;

    Set<UserDto> users;

}
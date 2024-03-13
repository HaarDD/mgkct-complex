package by.haardd.cclog.dto;

import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Value
public class RoleDto implements Serializable {

    @NotNull
    @Size(max = 50)
    String name;

    PriorityDto priority;

    Set<RegisterUserDto> users;

}
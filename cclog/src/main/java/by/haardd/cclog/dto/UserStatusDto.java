package by.haardd.cclog.dto;

import by.haardd.cclog.entity.enums.UserStatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Value
public class UserStatusDto implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;

    @NotNull
    UserStatusEnum name;
}
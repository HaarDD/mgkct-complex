package by.haardd.cclog.dto;

import by.haardd.cclog.entity.enums.UserStatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Value
public class UserStatusDto implements Serializable {

    Long id;

    @Size(max = 50)
    @NotNull
    UserStatusEnum name;
}
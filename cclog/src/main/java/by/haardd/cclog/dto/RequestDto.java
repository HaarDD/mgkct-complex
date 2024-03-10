package by.haardd.cclog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

@Value
public class RequestDto implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;

    @NotNull
    @Size(max = 50)
    String title;

    String description;

    String json;

    @NotNull
    Instant deadlineDate;

    @NotNull
    Instant createdAt;

    String engineerComment;

    @NotNull
    RequestTypeDto requestType;

    @NotNull
    UserDto createdByUser;

    @NotNull
    PriorityDto priority;

    @NotNull
    StatusDto status;

}
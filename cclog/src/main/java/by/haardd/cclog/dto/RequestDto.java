package by.haardd.cclog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

@Value
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestDto implements Serializable {


    Long id;

    @NotNull
    @Size(max = 50)
    String title;

    String description;

    String json;

    @NotNull
    Timestamp deadlineDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Timestamp createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String engineerComment;

    @NotNull
    RequestTypeDto requestType;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    UserDto createdByUser;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    PriorityDto priority;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    StatusDto status;

}
package by.haardd.cclog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Set;

@Data
@Accessors(chain = true)
public class RegisterUserDto implements Serializable {

    @NotNull
    @Size(max = 50)
    String login;

    @NotNull
    @Size(max = 255)
    String password;

    @Size(max = 50)
    String firstName;

    @Size(max = 50)
    String lastName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Timestamp createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Timestamp updatedAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Set<RequestDto> requests;
}
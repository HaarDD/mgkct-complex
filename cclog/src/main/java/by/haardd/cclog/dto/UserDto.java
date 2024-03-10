package by.haardd.cclog.dto;

import by.haardd.cclog.dto.RequestDto;
import by.haardd.cclog.dto.RoleDto;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

@Value
public class UserDto implements Serializable {

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

    @Size(max = 255)
    String refreshToken;

    @NotNull
    Instant refreshTokenExpiration;

    @NotNull
    RoleDto role;

    @NotNull
    Instant createdAt;

    @NotNull
    Instant updatedAt;

    Set<RequestDto> requests;
}
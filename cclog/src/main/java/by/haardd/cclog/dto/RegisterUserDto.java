package by.haardd.cclog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Accessors(chain = true)
public class RegisterUserDto implements Serializable {

    @NotNull
    @Pattern(regexp = "^[a-z0-9_-]{3,15}$", message = "Login must be alphanumeric string that may include _ and - having a length of 3 to 16 characters")
    @Size(max = 50)
    String login;

    @NotNull
    @Pattern(regexp = "^.*(?=.*\\d)(?=.*[a-zA-Z])(?=.*[\\W_])\\S{8,}.*$", message = "The password must consist of at least 8 characters, one number, one letter and one special character.")
    @Size(max = 255)
    String password;

    @Size(max = 50)
    @Pattern(regexp = "^[а-яА-Яa-zA-Z]{2,20}$", message = "Firstname must be between 2 and 20 characters of russian and english alphabetic")
    String firstName;

    @Size(max = 50)
    @Pattern(regexp = "^[а-яА-Яa-zA-Z]{2,20}$", message = "Lastname must be between 2 and 20 characters of russian and english alphabetic")
    String lastName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Timestamp createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Timestamp updatedAt;

}
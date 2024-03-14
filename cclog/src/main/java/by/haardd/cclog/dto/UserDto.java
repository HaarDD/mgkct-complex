package by.haardd.cclog.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class UserDto extends RegisterUserDto{

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Set<RequestDto> requests;

    @Override
    @JsonIgnore
    public String getPassword() {
        return super.getPassword();
    }
}

package by.haardd.cclog.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDto extends RegisterUserDto{

    @Override
    @JsonIgnore
    public String getPassword() {
        return super.getPassword();
    }
}

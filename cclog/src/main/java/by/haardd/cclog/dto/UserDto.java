package by.haardd.cclog.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class UserDto extends RegisterUserDto {

    @Override
    @JsonIgnore
    public String getPassword() {
        return super.getPassword();
    }
}

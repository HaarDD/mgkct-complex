package by.haardd.cclog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Value
public class PriorityDto implements Serializable {

    Long id;

    @NotNull
    @Size(max = 50)
    String name;

    @NotNull
    Long value;

    @NotNull
    @Size(max = 6)
    @Pattern(regexp = "[A-Fa-f0-9]{6}|[A-Fa-f0-9]{3}", message = "Must be a HEX (without #)")
    String colorHex;

}
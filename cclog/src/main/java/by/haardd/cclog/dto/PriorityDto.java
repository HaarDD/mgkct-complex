package by.haardd.cclog.dto;

import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Value
public class PriorityDto implements Serializable {

    @NotNull
    @Size(max = 50)
    String name;

    @NotNull
    Long value;

    @NotNull
    @Size(max = 6)
    String colorHex;

    Set<RequestDto> requests;

}
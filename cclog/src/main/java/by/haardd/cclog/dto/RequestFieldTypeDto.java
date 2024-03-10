package by.haardd.cclog.dto;

import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Value
public class RequestFieldTypeDto implements Serializable {

    @NotNull
    @Size(max = 50)
    String name;

    Set<RequestTypeDto> requestTypes;
}
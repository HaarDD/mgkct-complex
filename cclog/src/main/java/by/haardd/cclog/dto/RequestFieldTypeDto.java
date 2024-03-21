package by.haardd.cclog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Value
public class RequestFieldTypeDto implements Serializable {

    Long id;

    @NotNull
    @Size(max = 50)
    String name;

    Set<RequestTypeDto> requestTypes;
}
package by.haardd.cclog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Value
public class RequestTypeDto implements Serializable {

    Long id;

    @NotNull
    @Size(max = 50)
    String name;

    @NotNull
    @Size(max = 50)
    String readableName;

    String description;

    //TODO доступные поля
}
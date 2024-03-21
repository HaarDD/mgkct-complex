package by.haardd.cclog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RequestListWithTotal {

    private Long totalCount;

    private List<RequestDto> requests;

}

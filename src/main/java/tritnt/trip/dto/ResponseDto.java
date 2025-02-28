package tritnt.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto<T> {
    private int code;
    private String message;
    private T data;
}

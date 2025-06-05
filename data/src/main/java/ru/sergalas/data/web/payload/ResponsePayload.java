package ru.sergalas.data.web.payload;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sergalas.data.web.data.ResponseData;

@NoArgsConstructor
@Data
@Builder
public class ResponsePayload {
    private Integer code;
    private ResponseData data;
    private String error;

    public ResponsePayload(Integer code, ResponseData data) {
        this.data = data;
        this.code = code;
    }

    public ResponsePayload(Integer code, String error) {
        this.code = code;
        this.error = error;
    }


}

package com.tcc.uffmaterias.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.List;
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErroTemplate {
    private String title;
    private String detail;
    private OffsetDateTime timestamp;
    private HttpStatus httpStatus;
    private Integer httpStatusCode;
    private List<Field> fields;

    @Getter
    @Builder
    public static class Field{
        private String name;
        private String userMessage;
    }
}

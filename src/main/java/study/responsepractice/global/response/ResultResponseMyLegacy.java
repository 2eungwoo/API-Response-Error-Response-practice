package study.responsepractice.global.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
@Getter
// ResultResponse<T> : don't use raw type
public class ResultResponseMyLegacy<T> {

    private final HttpStatus status;
    private final String code;
    private final String message;
    private final T data;

}
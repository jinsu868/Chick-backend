package story.cheek.common.exception.globlacontrolleradvice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import story.cheek.common.exception.BusinessException;
import story.cheek.common.exception.ErrorCode;
import story.cheek.common.exception.ErrorResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleRuntimeException(BusinessException e) {
        final ErrorCode errorCode = e.getErrorCode();

        final ErrorResponse response = ErrorResponse.builder()
                .businessCode(errorCode.getCode())
                .errorMessage(errorCode.getMessage())
                .build();
        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }
}

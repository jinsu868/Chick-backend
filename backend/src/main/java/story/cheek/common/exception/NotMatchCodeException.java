package story.cheek.common.exception;

public class NotMatchCodeException extends BusinessException {
    public NotMatchCodeException(ErrorCode errorCode) {
        super(errorCode);
    }
}

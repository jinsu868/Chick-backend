package story.cheek.common.exception;

public class NotFoundMailVerificationException extends BusinessException {
    public NotFoundMailVerificationException(ErrorCode errorCode) {
        super(errorCode);
    }
}

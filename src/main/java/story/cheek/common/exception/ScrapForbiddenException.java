package story.cheek.common.exception;

public class ScrapForbiddenException extends BusinessException {
    public ScrapForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}

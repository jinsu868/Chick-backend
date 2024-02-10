package story.cheek.common.exception;

public class NotFoundStoryException extends BusinessException {
    public NotFoundStoryException(ErrorCode errorCode) {
        super(errorCode);
    }
}

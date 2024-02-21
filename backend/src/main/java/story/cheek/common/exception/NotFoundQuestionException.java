package story.cheek.common.exception;

public class NotFoundQuestionException extends BusinessException {
    public NotFoundQuestionException(ErrorCode errorCode) {
        super(errorCode);
    }
}

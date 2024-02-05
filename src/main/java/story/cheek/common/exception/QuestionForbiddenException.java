package story.cheek.common.exception;

public class QuestionForbiddenException extends BusinessException {
    public QuestionForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}

package story.cheek.common.exception;

public class StoryForbiddenException extends BusinessException {
    public StoryForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}

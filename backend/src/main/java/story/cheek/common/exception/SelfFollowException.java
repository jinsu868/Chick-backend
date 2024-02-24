package story.cheek.common.exception;

public class SelfFollowException extends BusinessException{
    public SelfFollowException(ErrorCode errorCode) {
        super(errorCode);
    }
}

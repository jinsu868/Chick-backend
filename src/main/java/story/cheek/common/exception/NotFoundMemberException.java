package story.cheek.common.exception;

public class NotFoundMemberException extends BusinessException{
    public NotFoundMemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}

package story.cheek.common.exception;

public class NotAdminException extends BusinessException {
    public NotAdminException(ErrorCode errorCode) {
        super(errorCode);
    }
}

package story.cheek.common.exception;

public class NotFoundApplication extends BusinessException {
    public NotFoundApplication(ErrorCode errorCode) {
        super(errorCode);
    }
}

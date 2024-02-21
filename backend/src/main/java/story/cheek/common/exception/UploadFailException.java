package story.cheek.common.exception;

public class UploadFailException extends BusinessException {
    public UploadFailException(ErrorCode errorCode) {
        super(errorCode);
    }
}

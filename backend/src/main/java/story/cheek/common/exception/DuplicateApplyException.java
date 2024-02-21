package story.cheek.common.exception;

public class DuplicateApplyException extends BusinessException {
    public DuplicateApplyException(ErrorCode errorCode) {
        super(errorCode);
    }
}

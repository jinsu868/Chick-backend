package story.cheek.common.exception;

public class DuplicateApprovalException extends BusinessException {
    public DuplicateApprovalException(ErrorCode errorCode) {
        super(errorCode);
    }
}

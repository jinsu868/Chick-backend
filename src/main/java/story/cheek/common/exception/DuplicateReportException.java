package story.cheek.common.exception;

public class DuplicateReportException extends BusinessException{
    public DuplicateReportException(ErrorCode errorCode) {
        super(errorCode);
    }
}

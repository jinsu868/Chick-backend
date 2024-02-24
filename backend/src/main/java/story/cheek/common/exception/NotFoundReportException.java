package story.cheek.common.exception;

public class NotFoundReportException extends BusinessException{
    public NotFoundReportException(ErrorCode errorCode) {
        super(errorCode);
    }
}

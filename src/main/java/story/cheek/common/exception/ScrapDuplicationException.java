package story.cheek.common.exception;

public class ScrapDuplicationException extends BusinessException {
    public ScrapDuplicationException(ErrorCode errorCode) {
        super(errorCode);
    }
}

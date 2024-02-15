package story.cheek.common.exception;

public class MailSendException extends BusinessException {
    public MailSendException(ErrorCode errorCode) {
        super(errorCode);
    }
}

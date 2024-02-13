package story.cheek.common.exception;

public class NotFoundCompanyDomainException extends BusinessException {
    public NotFoundCompanyDomainException(ErrorCode errorCode) {
        super(errorCode);
    }
}

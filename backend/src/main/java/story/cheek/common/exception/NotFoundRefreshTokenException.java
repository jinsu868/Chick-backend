package story.cheek.common.exception;

public class NotFoundRefreshTokenException extends BusinessException{
    public NotFoundRefreshTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}

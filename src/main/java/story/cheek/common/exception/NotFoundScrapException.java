package story.cheek.common.exception;

import story.cheek.common.exception.BusinessException;
import story.cheek.common.exception.ErrorCode;

public class NotFoundScrapException extends BusinessException {
    public NotFoundScrapException(ErrorCode errorCode) {
        super(errorCode);
    }
}

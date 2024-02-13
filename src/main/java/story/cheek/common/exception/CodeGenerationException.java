package story.cheek.common.exception;

public class CodeGenerationException extends BusinessException {
    public CodeGenerationException(ErrorCode errorCode) {
        super(errorCode);
    }
}

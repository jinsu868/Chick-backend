package story.cheek.common.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNAUTHORIZED_REDIRECT_URI(400, "AU_001", "인증되지 않은 REDIRECT_URI입니다."),
    MEMBER_NOT_FOUND(400, "C_001", "Member를 찾을 수 없습니다."),
    ENTITY_NOT_FOUND(400, "C_002", "지정한 Entity를 찾을 수 없습니다.");

    private final int status;
    private final String code;
    private final String message;
}

package story.cheek.common.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNAUTHORIZED_REDIRECT_URI(400, "AU_001", "인증되지 않은 REDIRECT_URI입니다."),

    DUPLICATED_REPORT(409, "RE_001", "이미 신고한 유저입니다"),

    MEMBER_NOT_FOUND(400, "C_001", "Member를 찾을 수 없습니다."),

    ENTITY_NOT_FOUND(400, "C_002", "지정한 Entity를 찾을 수 없습니다."),

    QUESTION_NOT_FOUND(400, "C_003", "Question을 찾을 수 없습니다."),

    FORBIDDEN_QUESTION_UPDATE(401, "C_004", "질문을 수정할 권한이 없습니다."),

    STORY_NOT_FOUND(400, "C_006", "Story를 찾을 수 없습니다."),

    FORBIDDEN_STORY_CREATE(401, "C_005", "스토리를 생성할 권한이 없습니다."),

    FORBIDDEN_SCRAP_DELETE(401, "C_008", "스크랩을 삭제할 권한이 없습니다."),

    ALREADY_STORY_SCRAP(400, "C_006", "이미 해당 스토리를 스크랩했습니다."),

    SCRAP_NOT_FOUND(400, "C_007", "Scrap을 찾을 수 없습니다."),

    FAILED_IMAGE_UPLOAD(400, "C_009", "이미지 업로드에 실패했습니다."),

    INVALID_FILE_EXTENSION(400, "C_010", "유효하지 않은 파일 확장자입니다."),

    FOLLOW_NOT_FOUND(400, "C_011", "Follow를 찾을 수 없습니다."),

    FOLLOWING_NOT_FOUND(400, "C_012", "아무도 팔로잉 하지않고 있습니다."),

    FOLLOWER_NOT_FOUND(400, "C_013", "팔로워가 없습니다"),

    DUPLICATED_FOLLOW(400, "C_014", "이미 팔로우한 유저입니다"),

    FORBIDDEN_FOLLOW_DELETE(401, "C_015", "Follow를 삭제할 권한이 없습니다.");

    private final int status;
    private final String code;
    private final String message;
}

package story.cheek.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    ACTIVE("활성 상태인 멤버"),
    SUSPENDED("정지된 상태인 멤버");
//    INACTIVE: 비활성 상태인 멤버
//    BLOCKED: 차단된 상태인 멤버
//    PENDING: 보류 중인 상태인 멤버 (예: 회원 가입 승인 대기 중인 경우)
//    TERMINATED: 계정이 종료된 상태인 멤버


    private final String content;
}

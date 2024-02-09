package story.cheek.member.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import story.cheek.common.domain.BaseEntity;
import story.cheek.member.dto.MemberBasicInfoUpdateRequest;
import story.cheek.question.domain.Occupation;
import story.cheek.report.domain.Report;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Occupation occupation;

    private String name;

    private String email;

    private String image;

    private String description;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private boolean isMentor;

    @OneToMany(mappedBy = "reportingMember")
    private List<Report> reportingList = new ArrayList<>();

    @OneToMany(mappedBy = "reportedMember")
    private List<Report> reportedList = new ArrayList<>();

    public String roleName() {
        return role.name();
    }

    public void updateImage(MultipartFile file) {
        this.image = file.getOriginalFilename();
    }

    public void updateBasicInfo(MemberBasicInfoUpdateRequest memberBasicInfoUpdateRequest) {
        this.name = memberBasicInfoUpdateRequest.name();
        this.occupation = memberBasicInfoUpdateRequest.occupation();
        this.description = memberBasicInfoUpdateRequest.description();
    }

    public int getReportedListCount() {
        return this.reportedList.size();
    }

    public void changeStatusToSuspend() {
        this.status = Status.SUSPENDED;
    }

    public void addReportedList(Report report) {
        this.reportedList.add(report);
    }

    public void addReportingList(Report report) {
        this.reportingList.add(report);
    }


}

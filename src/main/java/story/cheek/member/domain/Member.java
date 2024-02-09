package story.cheek.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import story.cheek.question.domain.Occupation;
import story.cheek.report.domain.Report;

import java.util.ArrayList;
import java.util.List;

import static story.cheek.member.domain.Status.ACTIVE;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Occupation occupation;

    private String name;

    private String email;

    private String image;

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

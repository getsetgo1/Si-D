package org.devjeans.sid.domain.project.entity;

import lombok.*;
import org.devjeans.sid.domain.chatRoom.entity.ChatRoom;
import org.devjeans.sid.domain.common.BaseEntity;
import org.devjeans.sid.domain.mainPage.dto.TopListMemberResponse;
import org.devjeans.sid.domain.mainPage.dto.TopListProjectResponse;
import org.devjeans.sid.domain.member.entity.Member;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Project extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="project_id")
    private Long id;

    private String projectName;

    @Column(nullable = false)
    private String description;


    @Column(nullable = false, length = 5000)
    private String recruitmemtContents;

    @Column(columnDefinition = "varchar(1) default 'N'")
    private String isClosed;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @ColumnDefault("0")
    private Long views=0L;

    @ColumnDefault("0")
    private Long scrapCount=0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pm_id")
    private Member pm;

    @Builder.Default
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectMember> projectMembers = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<RecruitInfo> recruitInfos = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectScrap> projectScraps = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "project", cascade = CascadeType.PERSIST)
    private List<ChatRoom> chatRooms = new ArrayList<>();

    public void updateNewProjectMembers(List<ProjectMember> newProjectMembers){
        this.projectMembers = newProjectMembers;
    }
    public void updateRecruitInfos(List<RecruitInfo> recruitInfos){
        this.recruitInfos = recruitInfos;
    }

    public void updateIsClosed(String yn){
        this.isClosed=yn;
    }

    public static TopListProjectResponse topListResFromEntity (Project project){
        // 완성된 프로젝트 글 내용 30자까지만 잘라서 출력
        String description = project.getDescription();
        String truncatedDescription = description != null && description.length() > 30 ? description.substring(0, 30) : description;
        return TopListProjectResponse.builder()
                .id(project.getId())
                .projectName(project.getProjectName())
                .description(truncatedDescription)
                .build();
    }


}

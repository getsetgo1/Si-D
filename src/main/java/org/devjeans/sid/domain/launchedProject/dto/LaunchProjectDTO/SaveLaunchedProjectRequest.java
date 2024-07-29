package org.devjeans.sid.domain.launchedProject.dto.LaunchProjectDTO;

import lombok.*;
import org.devjeans.sid.domain.launchedProject.dto.LaunchedProjectMemberDTO.LaunchedProjectMemberRequest;
import org.devjeans.sid.domain.launchedProject.entity.LaunchedProject;
import org.devjeans.sid.domain.launchedProject.entity.LaunchedProjectTechStack;
import org.devjeans.sid.domain.project.entity.Project;
import org.devjeans.sid.domain.project.entity.ProjectMember;
import org.devjeans.sid.domain.siderCard.entity.JobField;
import org.devjeans.sid.domain.siderCard.entity.TechStack;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveLaunchedProjectRequest {

    private Long projectId; // 프로젝트의 id (project테이블 id FK)

//    private MultipartFile launchedProjectImage; // 프로젝트 사진 -> 이후 String으로 변환필요

    private String launchedProjectContents; // Launched-Project 글 내용

    private String siteUrl; // 프로젝트 사이트 링크

    private List<LaunchedProjectMemberRequest> members; // 프로젝트에 참여한 멤버 (LaunchedProject에 등록할 때 새로 멤버를 추가할 수도 있음) -> ProjectMember 교차테이블에 추가해주기 위함

    private List<LaunchedProjectTechStackRequest> launchedProjectTechStackRequestList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    // 프로젝트 기술스택 Request DTO
    public static class LaunchedProjectTechStackRequest{
        private Long id;
//        private JobField jobField; // 직무 유형
//        private String techStackName; // 기술스택 명
    }


    // SaveLaunchedProjectRequest(DTO) -> LaunchedProject
    public static LaunchedProject toEntity(SaveLaunchedProjectRequest dto,
                                           String imagePath,
                                           Project project,
                                           List<LaunchedProjectTechStack> launchedProjectTechStacks
                                           ){

        return LaunchedProject.builder()
                .launchedProjectImage(imagePath)
                .launchedProjectContents(dto.getLaunchedProjectContents())
                .siteUrl(dto.getSiteUrl())
                .project(project)
                .launchedProjectTechStacks(launchedProjectTechStacks)
                .build();
    }

    public static LaunchedProjectTechStack toLaunchedProjectTechStack (LaunchedProject launchedProject,
                                                                       TechStack techStack){
        return LaunchedProjectTechStack.builder()
                .launchedProject(launchedProject)
                .techStack(techStack)
                .build();
    }
}
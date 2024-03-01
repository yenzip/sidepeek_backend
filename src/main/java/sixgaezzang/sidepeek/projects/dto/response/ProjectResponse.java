package sixgaezzang.sidepeek.projects.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.YearMonth;
import java.util.List;
import lombok.Builder;
import sixgaezzang.sidepeek.projects.domain.Project;

@Schema(description = "프로젝트 상세조회 응답")
@Builder
public record ProjectResponse(
    @Schema(description = "프로젝트 식별자", example = "1")
    Long id,
    @Schema(description = "프로젝트 제목", example = "사이드픽👀")
    String name,
    @Schema(description = "프로젝트 부제목", example = "좋은 아이디어? 사이드픽에서 찾아봐!")
    String subName,
    @Schema(description = "프로젝트 개요", example = "사이드 프로젝트를 공유하는 사이드픽입니다.")
    String overview,
    @Schema(description = "프로젝트 썸네일 이미지 URL", example = "https://sidepeek.image/imageeUrl")
    String thumbnailUrl,
    @Schema(description = "프로젝트 레이아웃 이미지 URL 목록")
    List<OverviewImageSummary> overviewImageUrl,
    @Schema(description = "프로젝트 Github URL", example = "https://github.com/side-peek")
    String githubUrl,
    @Schema(description = "프로젝트 배포 URL", example = "https://www.sidepeek.com")
    String deployUrl,
    @Schema(description = "프로젝트 조회수", example = "1")
    Long viewCount,
    @Schema(description = "프로젝트 좋아요수", example = "0")
    Long likeCount,
    @Schema(description = "프로젝트 기술 스택 목록")
    List<ProjectSkillSummary> techStacks,
    @Schema(description = "프로젝트 시작 연-월", example = "2024-02")
    YearMonth startDate,
    @Schema(description = "프로젝트 종료 연-월", example = "2024-03")
    YearMonth endDate,
    @Schema(description = "프로젝트 작성자 Id", example = "1")
    Long ownerId,
    @Schema(description = "프로젝트 멤버 목록")
    List<MemberSummary> members,
    @Schema(description = "프로젝트 기능 설명", example = "## 사이드픽 기능 설명 Markdown")
    String description,
    @Schema(description = "프로젝트 트러블 슈팅", example = "## 사이드픽 트러블 슈팅 Markdown")
    String troubleShooting
) {

    public static ProjectResponse from(Project project, List<OverviewImageSummary> overviewImageUrl,
                                       List<ProjectSkillSummary> techStacks, List<MemberSummary> members) {
        return ProjectResponse.builder()
            .id(project.getId())
            .name(project.getName())
            .subName(project.getSubName())
            .overview(project.getOverview())
            .thumbnailUrl(project.getThumbnailUrl())
            .overviewImageUrl(overviewImageUrl)
            .githubUrl(project.getGithubUrl())
            .deployUrl(project.getDeployUrl())
            .viewCount(project.getViewCount())
            .likeCount(project.getLikeCount())
            .techStacks(techStacks)
            .ownerId(project.getOwnerId())
            .startDate(project.getStartDate())
            .endDate(project.getEndDate())
            .members(members)
            .description(project.getDescription())
            .troubleShooting(project.getTroubleshooting())
            .build();
    }

}

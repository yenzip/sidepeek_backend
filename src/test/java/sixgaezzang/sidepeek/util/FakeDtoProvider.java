package sixgaezzang.sidepeek.util;

import static sixgaezzang.sidepeek.util.FakeValueProvider.createLongText;
import static sixgaezzang.sidepeek.util.FakeValueProvider.createNickname;
import static sixgaezzang.sidepeek.util.FakeValueProvider.createOverview;
import static sixgaezzang.sidepeek.util.FakeValueProvider.createProjectName;
import static sixgaezzang.sidepeek.util.FakeValueProvider.createRole;
import static sixgaezzang.sidepeek.util.FakeValueProvider.createSkillCategory;
import static sixgaezzang.sidepeek.util.FakeValueProvider.createUrl;

import java.time.YearMonth;
import java.util.List;
import sixgaezzang.sidepeek.projects.dto.request.SaveMemberRequest;
import sixgaezzang.sidepeek.projects.dto.request.SaveProjectRequest;
import sixgaezzang.sidepeek.projects.dto.request.SaveProjectSkillRequest;
import sixgaezzang.sidepeek.users.dto.request.UpdateUserSkillRequest;

public class FakeDtoProvider {

    // Project Skill
    public static SaveProjectSkillRequest createSaveProjectSkillRequest(Long skillId) {
        return new SaveProjectSkillRequest(skillId, createSkillCategory());
    }

    // User Skill
    public static UpdateUserSkillRequest createUpdateUserSkillRequest(Long skillId) {
        return new UpdateUserSkillRequest(skillId, createSkillCategory());
    }

    // Project
    public static SaveProjectRequest createProjectSaveRequestOnlyRequired(
        String name, String overview, String githubUrl, String description, Long ownerId,
        List<SaveProjectSkillRequest> techStacks, List<SaveMemberRequest> members
    ) {
        return new SaveProjectRequest(name, overview, ownerId, githubUrl, description,
            techStacks, null, null, null, null,
            null, null, null, members);
    }

    public static SaveProjectRequest createSaveProjectRequestWithOwnerIdAndOption(
        List<SaveProjectSkillRequest> techStacks, Long ownerId, String subName, String thumbnailUrl, String deployUrl,
        String troubleShooting, YearMonth startDate, YearMonth endDate
    ) {
        return new SaveProjectRequest(
            createProjectName(), createOverview(), ownerId,
            createUrl(), createLongText(), techStacks, subName,
            thumbnailUrl, deployUrl, startDate, endDate, troubleShooting, null, null
        );
    }

    // Member
    public static SaveMemberRequest createFellowSaveMemberRequest(Long userId) {
        return new SaveMemberRequest(userId, createNickname(), createRole());
    }

    public static SaveMemberRequest createNonFellowSaveMemberRequest() {
        return new SaveMemberRequest(null, createNickname(), createRole());
    }
}

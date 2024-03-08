package sixgaezzang.sidepeek.projects.util.validation;

import static sixgaezzang.sidepeek.projects.exception.message.ProjectSkillErrorMessage.PROJECT_TECH_STACKS_IS_NULL;
import static sixgaezzang.sidepeek.projects.exception.message.ProjectSkillErrorMessage.PROJECT_TECH_STACKS_OVER_MAX_COUNT;
import static sixgaezzang.sidepeek.projects.util.ProjectConstant.MAX_PROJECT_SKILL_COUNT;
import static sixgaezzang.sidepeek.skill.exception.message.SkillErrorMessage.CATEGORY_IS_NULL;
import static sixgaezzang.sidepeek.skill.exception.message.SkillErrorMessage.SKILL_ID_IS_NULL;

import io.jsonwebtoken.lang.Assert;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import sixgaezzang.sidepeek.common.util.ValidationUtils;
import sixgaezzang.sidepeek.projects.dto.request.SaveProjectSkillRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectSkillValidator {

    public static void validateTechStacks(List<SaveProjectSkillRequest> techStacks) {
        ValidationUtils.validateNotNullAndEmpty(techStacks, PROJECT_TECH_STACKS_IS_NULL);

        Assert.isTrue(techStacks.size() < MAX_PROJECT_SKILL_COUNT, PROJECT_TECH_STACKS_OVER_MAX_COUNT);

        techStacks.forEach(ProjectSkillValidator::validateTechStack);
    }

    public static void validateTechStack(SaveProjectSkillRequest techStack) {
        Assert.notNull(techStack.skillId(), SKILL_ID_IS_NULL);
        Assert.notNull(techStack.category(), CATEGORY_IS_NULL);
    }
}

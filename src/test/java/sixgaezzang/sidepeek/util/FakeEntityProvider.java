package sixgaezzang.sidepeek.util;

import static io.micrometer.common.util.StringUtils.isBlank;

import java.time.YearMonth;
import net.datafaker.Faker;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sixgaezzang.sidepeek.comments.domain.Comment;
import sixgaezzang.sidepeek.projects.domain.Project;
import sixgaezzang.sidepeek.projects.domain.member.Member;
import sixgaezzang.sidepeek.skill.domain.Skill;
import sixgaezzang.sidepeek.users.domain.Password;
import sixgaezzang.sidepeek.users.domain.User;

public class FakeEntityProvider {

    private static final Faker FAKER = new Faker();

    public static Project createProject(User user) {
        YearMonth startDate = YearMonth.now();
        YearMonth endDate = startDate.plusMonths(3);

        return Project.builder()
            .name(FakeValueProvider.createProjectName())
            .subName(FakeValueProvider.createProjectName())
            .overview(FakeValueProvider.createOverview())
            .thumbnailUrl(FakeValueProvider.createUrl())
            .githubUrl(FakeValueProvider.createUrl())
            .startDate(startDate)
            .endDate(endDate)
            .ownerId(user.getId())
            .description(FakeValueProvider.createLongText())
            .build();
    }

    public static User createUser() {
        String email = FakeValueProvider.createEmail();
        String password = FakeValueProvider.createPassword();

        return User.builder()
            .email(email)
            .password(new Password(password, new BCryptPasswordEncoder()))
            .nickname(FakeValueProvider.createNickname())
            .build();
    }

    public static User createUser(
        String email, String password, String nickname, PasswordEncoder passwordEncoder
    ) {
        return User.builder()
            .email(isBlank(email) ? FakeValueProvider.createEmail() : email)
            .password(isBlank(password) ? new Password(FakeValueProvider.createPassword(), passwordEncoder)
                : new Password(password, passwordEncoder))
            .nickname(isBlank(nickname) ? FakeValueProvider.createNickname() : nickname)
            .build();
    }

    public static Skill createSkill() {
        return Skill.builder()
            .name(FakeValueProvider.createSkillName())
            .iconImageUrl(FakeValueProvider.createUrl())
            .build();
    }

    public static Comment createComment(User user, Project project) {
        return Comment.builder()
            .user(user)
            .project(project)
            .content(FakeValueProvider.createContent())
            .build();
    }

    public static Member createMember(User user, Project project) {
        return Member.builder()
            .user(user)
            .nickname(user.getNickname())
            .project(project)
            .role(FakeValueProvider.createRole())
            .build();
    }
}

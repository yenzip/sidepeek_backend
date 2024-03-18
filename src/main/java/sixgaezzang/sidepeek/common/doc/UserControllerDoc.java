package sixgaezzang.sidepeek.common.doc;

import static sixgaezzang.sidepeek.common.doc.description.response.ResponseCodeDescription.BAD_REQUEST_400_DESCRIPTION;
import static sixgaezzang.sidepeek.common.doc.description.response.ResponseCodeDescription.CREATED_201_DESCRIPTION;
import static sixgaezzang.sidepeek.common.doc.description.response.ResponseCodeDescription.FORBIDDEN_403_DESCRIPTION;
import static sixgaezzang.sidepeek.common.doc.description.response.ResponseCodeDescription.NOT_FOUND_404_DESCRIPTION;
import static sixgaezzang.sidepeek.common.doc.description.response.ResponseCodeDescription.NO_CONTENT_204_DESCRIPTION;
import static sixgaezzang.sidepeek.common.doc.description.response.ResponseCodeDescription.OK_200_DESCRIPTION;
import static sixgaezzang.sidepeek.common.doc.description.response.ResponseCodeDescription.UNAUTHORIZED_401_DESCRIPTION;
import static sixgaezzang.sidepeek.common.doc.description.UserDescription.PROJECTS_PAGE_NUMBER_DESCRIPTION;
import static sixgaezzang.sidepeek.common.doc.description.UserDescription.PROJECTS_PAGE_SIZE_DESCRIPTION;
import static sixgaezzang.sidepeek.common.doc.description.UserDescription.PROJECTS_TYPE_DESCRIPTION;
import static sixgaezzang.sidepeek.common.doc.description.UserDescription.USER_KEYWORD_DESCRIPTION;
import static sixgaezzang.sidepeek.users.exception.message.UserErrorMessage.NICKNAME_OVER_MAX_LENGTH;
import static sixgaezzang.sidepeek.users.util.UserConstant.MAX_NICKNAME_LENGTH;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import sixgaezzang.sidepeek.common.dto.response.Page;
import sixgaezzang.sidepeek.common.exception.ErrorResponse;
import sixgaezzang.sidepeek.projects.domain.UserProjectSearchType;
import sixgaezzang.sidepeek.projects.dto.response.ProjectListResponse;
import sixgaezzang.sidepeek.users.dto.request.CheckEmailRequest;
import sixgaezzang.sidepeek.users.dto.request.CheckNicknameRequest;
import sixgaezzang.sidepeek.users.dto.request.SignUpRequest;
import sixgaezzang.sidepeek.users.dto.request.UpdatePasswordRequest;
import sixgaezzang.sidepeek.users.dto.request.UpdateUserProfileRequest;
import sixgaezzang.sidepeek.users.dto.response.CheckDuplicateResponse;
import sixgaezzang.sidepeek.users.dto.response.UserProfileResponse;
import sixgaezzang.sidepeek.users.dto.response.UserSearchResponse;

@Tag(name = "User", description = "사용자 API")
public interface UserControllerDoc {
    @Operation(summary = "회원가입")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = CREATED_201_DESCRIPTION, useReturnTypeSchema = true),
        @ApiResponse(responseCode = "400", description = BAD_REQUEST_400_DESCRIPTION, content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<Void> signUp(@Valid SignUpRequest request);

    @Operation(summary = "비밀번호 수정", description = "로그인 필수")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = NO_CONTENT_204_DESCRIPTION),
        @ApiResponse(responseCode = "400", description = BAD_REQUEST_400_DESCRIPTION, content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = UNAUTHORIZED_401_DESCRIPTION, content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = FORBIDDEN_403_DESCRIPTION, content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = NOT_FOUND_404_DESCRIPTION, content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Parameter(name = "id", description = "수정할 회원 식별자", example = "1")
    ResponseEntity<Void> updatePassword(@Parameter(hidden = true) Long loginId, Long id,
                                        @Valid UpdatePasswordRequest request);

    @Operation(summary = "이메일 중복 확인")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = OK_200_DESCRIPTION),
        @ApiResponse(responseCode = "400", description = BAD_REQUEST_400_DESCRIPTION, content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<CheckDuplicateResponse> checkEmailDuplicate(@Valid CheckEmailRequest request);

    @Operation(summary = "닉네임 중복 확인")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = OK_200_DESCRIPTION),
        @ApiResponse(responseCode = "400", description = BAD_REQUEST_400_DESCRIPTION, content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<CheckDuplicateResponse> checkNicknameDuplicate(@Valid CheckNicknameRequest request);

    @Operation(summary = "회원 검색", description = "회원 닉네임 검색")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = OK_200_DESCRIPTION),
        @ApiResponse(responseCode = "400", description = BAD_REQUEST_400_DESCRIPTION, content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Parameter(name = "keyword", description = USER_KEYWORD_DESCRIPTION, example = "sixgaezzang6", in = ParameterIn.QUERY)
    ResponseEntity<UserSearchResponse> searchByNickname(
        @Size(max = MAX_NICKNAME_LENGTH, message = NICKNAME_OVER_MAX_LENGTH)
        String keyword
    );

    @Operation(summary = "회원 프로필 상세 정보 조회", description = "로그인 선택")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = OK_200_DESCRIPTION),
        @ApiResponse(responseCode = "400", description = BAD_REQUEST_400_DESCRIPTION, content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Parameter(name = "id", description = "조회할 회원 식별자", example = "1", in = ParameterIn.PATH)
    ResponseEntity<UserProfileResponse> getById(Long id);

    @Operation(summary = "회원 프로필 정보 수정", description = "로그인 필수")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = OK_200_DESCRIPTION),
        @ApiResponse(responseCode = "400", description = BAD_REQUEST_400_DESCRIPTION, content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = UNAUTHORIZED_401_DESCRIPTION, content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = FORBIDDEN_403_DESCRIPTION, content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = NOT_FOUND_404_DESCRIPTION, content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Parameter(name = "id", description = "수정할 회원 식별자, 로그인 회원 식별자와 일치 검사", example = "1", in = ParameterIn.PATH)
    ResponseEntity<UserProfileResponse> update(@Parameter(hidden = true) Long loginId, Long id,
                                               @Valid UpdateUserProfileRequest request);

    @Operation(summary = "회원 프로젝트 조회", description = "회원과 관련된 프로젝트 목록 조회(페이지네이션), 로그인 선택")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = OK_200_DESCRIPTION),
        @ApiResponse(responseCode = "400", description = BAD_REQUEST_400_DESCRIPTION, content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = UNAUTHORIZED_401_DESCRIPTION, content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = NOT_FOUND_404_DESCRIPTION, content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Parameters({
        @Parameter(name = "id", description = "조회할 회원 식별자", example = "1", in = ParameterIn.PATH),
        @Parameter(name = "type", description = PROJECTS_TYPE_DESCRIPTION, in = ParameterIn.QUERY),
        @Parameter(name = "page", description = PROJECTS_PAGE_NUMBER_DESCRIPTION, in = ParameterIn.QUERY),
        @Parameter(name = "size", description = PROJECTS_PAGE_SIZE_DESCRIPTION, in = ParameterIn.QUERY)
    })
    ResponseEntity<Page<ProjectListResponse>> getProjects(
        @Parameter(hidden = true) Long loginId,
        Long id, UserProjectSearchType type, @Parameter(hidden = true) Pageable pageable);
}

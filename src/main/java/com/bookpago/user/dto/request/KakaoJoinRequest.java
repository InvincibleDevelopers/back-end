package invincibleDevs.bookpago.users.dto.request;

import invincibleDevs.bookpago.users.domain.Gender;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;


//카카오만해당?@ApiModelProperty(value = "카카오 회원 고유 ID", required = true)
public record KakaoJoinRequest(
        @NotBlank String username,

        @ApiModelProperty(value = "프로필 닉네임. 중복불가", required = true)
        @NotBlank String nickname,

        @ApiModelProperty(value = "카카오 액세스 토큰", required = true)
        @NotBlank
        String kakaoOauthToken,

        @ApiModelProperty(value = "성별", required = true) @NotBlank
        Gender gender,

        @ApiModelProperty(value = "나이", required = true)
        @NotBlank LocalDate birth,
        String introduce) {

}

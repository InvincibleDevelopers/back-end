package com.bookpago.user.dto.request;

import com.bookpago.user.domain.Gender;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public record KakaoJoinRequest(
        @NotBlank String username,
        @NotBlank String kakaoOauthToken,
        Gender gender,
        @NotBlank LocalDate birth,
        String introduce) {

}

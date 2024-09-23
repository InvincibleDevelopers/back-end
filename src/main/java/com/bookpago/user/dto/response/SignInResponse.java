package com.bookpago.user.dto.response;

import java.util.Optional;

public record SignInResponse(
        boolean isUser,
        Optional<String> username,
        Optional<String> nickname,
        Optional<String> serverToken,
        String imageUrl
) {

}

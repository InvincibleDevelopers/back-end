package com.bookpago.user.dto.response;

public record SignUpResponse(
        String username,
        String nickname,
        String serverToken
) {

}

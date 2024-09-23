package com.bookpago.user;

import com.bookpago.user.dto.request.KakaoJoinRequest;
import com.bookpago.user.dto.request.KakaoSignInRequest;
import com.bookpago.user.dto.response.SignInResponse;
import com.bookpago.user.dto.response.SignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    public SignInResponse signInUser(String username) {
        return userService.signInUser(username);
    }

    public SignInResponse kakaoLoginUser(KakaoSignInRequest kakaoSignInRequest) {
        try {
            return userService.kakaoSignInUser(kakaoSignInRequest);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid access token : " + e.getMessage());
            throw e;
        }
    }

    public SignUpResponse kakaoJoinUser(KakaoJoinRequest kakaoJoinRequest) {
        try {
            return userService.kakaoJoinUser(kakaoJoinRequest);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid access token : " + e.getMessage());
            throw e;
        }

    }


}

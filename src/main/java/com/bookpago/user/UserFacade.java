package invincibleDevs.bookpago.users.facade;

import invincibleDevs.bookpago.users.dto.request.KakaoJoinRequest;
import invincibleDevs.bookpago.users.dto.request.KakaoSignInRequest;
import invincibleDevs.bookpago.users.dto.response.SignInResponse;
import invincibleDevs.bookpago.users.dto.response.SignUpResponse;
import invincibleDevs.bookpago.users.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserEntityService userEntityService;

    public SignInResponse signInUser(String username) {
        return userEntityService.signInUser(username);
    }

    public SignInResponse kakaoLoginUser(KakaoSignInRequest kakaoSignInRequest) {
        try {
            return userEntityService.kakaoSignInUser(kakaoSignInRequest);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid access token : " + e.getMessage());
            // 예외를 다시 던지거나, 사용자에게 적절한 응답을 반환합니다.
            throw e;
        }
    }

    public SignUpResponse kakaoJoinUser(KakaoJoinRequest kakaoJoinRequest) {
        try {
            return userEntityService.kakaoJoinUser(kakaoJoinRequest);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid access token : " + e.getMessage());
            // 예외를 다시 던지거나, 사용자에게 적절한 응답을 반환합니다.
            throw e;
        }

    }


}

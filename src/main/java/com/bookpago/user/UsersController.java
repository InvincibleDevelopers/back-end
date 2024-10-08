package com.bookpago.user;

import com.bookpago.user.domain.UserRepository;
import com.bookpago.user.dto.request.KakaoJoinRequest;
import com.bookpago.user.dto.request.KakaoSignInRequest;
import com.bookpago.user.dto.response.SignInResponse;
import com.bookpago.user.dto.response.SignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UsersController {

    private final UserFacade userFacade;
    private final UserRepository userRepository;

    @GetMapping("/login")
    public ResponseEntity<SignInResponse> signIn(
            @RequestParam(value = "username") String username) {
        return ResponseEntity.ok(userFacade.signInUser(username));
    }

    @GetMapping("/kakaologin")
    public ResponseEntity<SignInResponse> kakaoLogin(
            @RequestHeader(value = "Authorization") String kakaoAccessToken) {
        KakaoSignInRequest kakaoSignInRequest = new KakaoSignInRequest(kakaoAccessToken);
        return ResponseEntity.ok(userFacade.kakaoLoginUser(kakaoSignInRequest));
    }

    @PostMapping("/kakaojoin")
    public ResponseEntity<SignUpResponse> kakaoJoin(  //프론트가 액세스토큰 body에 담아서 줌
            @RequestBody KakaoJoinRequest kakaoJoinRequest) {
        return ResponseEntity.ok(userFacade.kakaoJoinUser(kakaoJoinRequest));
    }
}

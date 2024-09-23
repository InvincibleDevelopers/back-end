package com.bookpago.user;

import com.bookpago.common.JWTUtil;
import com.bookpago.common.exception.CustomException;
import com.bookpago.user.domain.User;
import com.bookpago.user.domain.UserRepository;
import com.bookpago.user.dto.request.KakaoJoinRequest;
import com.bookpago.user.dto.request.KakaoSignInRequest;
import com.bookpago.user.dto.response.SignInResponse;
import com.bookpago.user.dto.response.SignUpResponse;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    public boolean isUserExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    //기능 : 유저아이디 널이면 , false반환. 유저아이디 잇으면 아디 이름 반환.
    @Transactional
    public SignInResponse signInUser(String username) {
        try {
            User user = userRepository.findByUsername(username);
            return new SignInResponse(true, Optional.ofNullable(user.getUsername()),
                    Optional.ofNullable(user.getNickname()), null, user.getProfileUrl());
        } catch (CustomException e) {
            return new SignInResponse(false, Optional.empty(), Optional.empty(), Optional.empty(),
                    null);
        }
    }

    @Transactional
    public SignInResponse kakaoSignInUser(
            KakaoSignInRequest kakaoSignInRequest) { //자동로그인 튕겼을때, 서버토큰 재발급
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + kakaoSignInRequest.kakaoAccessToken());
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange("https://kapi.kakao.com/v2/user/me",
                //post요청보낼 주소
                HttpMethod.POST, kakaoProfileRequest, String.class);
        System.out.println(response);

        // JSON 문자열 파싱
        JsonObject rootObject = JsonParser.parseString(response.getBody()).getAsJsonObject();

        // id와 nickname 추출
        long id = rootObject.get("id").getAsLong();
        String username = Long.toString(id);

        //DB엔 String타입 저장되어있음
        if (userRepository.existsByUsername(username)) {
            User user = userRepository.findByUsername(username);
            String serverToken = jwtUtil.createJwt(user.getUsername(), "USER",
                    60 * 60 * 1000 * 10L);
            return new SignInResponse(true, Optional.ofNullable(user.getUsername()),
                    Optional.ofNullable(user.getNickname()), Optional.ofNullable(serverToken),
                    user.getProfileUrl());
        }

        return new SignInResponse(false, Optional.empty(), Optional.empty(), Optional.empty(),
                null);
    }


    @Transactional
    public SignUpResponse kakaoJoinUser(KakaoJoinRequest kakaoJoinRequest) { // 첫 회원가입 시 서버토큰 생성
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + kakaoJoinRequest.kakaoOauthToken());
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange("https://kapi.kakao.com/v2/user/me",
                //post요청보낼 주소
                HttpMethod.POST, kakaoProfileRequest, String.class);
        System.out.println(response);

        // JSON 문자열 파싱
        JsonObject rootObject = JsonParser.parseString(response.getBody()).getAsJsonObject();

        // id와 nickname 추출
        long id = rootObject.get("id").getAsLong();
        String username = Long.toString(id);
        String nickname = rootObject.get("properties")
                .getAsJsonObject().get("nickname").getAsString();

        // 응답의 상태 코드 확인
        if (response.getStatusCode().is2xxSuccessful()) {
            User user = User.builder()
                    .username(username)
                    .nickname(nickname)
                    .gender(kakaoJoinRequest.gender())
                    .birth(kakaoJoinRequest.birth())
                    .build();
            userRepository.save(user);

            String serverToken = jwtUtil.createJwt(user.getUsername(), "USER", 60 * 1000L);
            return new SignUpResponse(user.getUsername(), user.getNickname(), serverToken);
        }

        throw new IllegalArgumentException("Invalid access token: " + response.getBody());
    }

    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }
}

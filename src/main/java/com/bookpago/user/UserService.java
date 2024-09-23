package invincibleDevs.bookpago.users.service;

import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import invincibleDevs.bookpago.common.JWTUtil;
import invincibleDevs.bookpago.common.exception.CustomException;
import invincibleDevs.bookpago.profile.model.Profile;
import invincibleDevs.bookpago.profile.repository.ProfileRepository;
import invincibleDevs.bookpago.users.domain.User;
import invincibleDevs.bookpago.users.dto.request.KakaoJoinRequest;
import invincibleDevs.bookpago.users.dto.request.KakaoSignInRequest;
import invincibleDevs.bookpago.users.dto.response.SignInResponse;
import invincibleDevs.bookpago.users.dto.response.SignUpResponse;
import invincibleDevs.bookpago.users.repository.UserRepository;
import java.time.LocalDateTime;
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
public class UserEntityService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final JWTUtil jwtUtil;

    public boolean isUserExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public SignInResponse signInUser(
            String username) { //기능 : 유저아이디 널이면 , false반환. 유저아이디 잇으면 아디 이름 반환.
        try {
//           String username = Utils.getAuthenticatedUsername();
//           System.out.println(signInRequest.serverToken());
//           System.out.println(username);
            User user = userRepository.findByUsername(username);
            return new SignInResponse(
                    true,
                    Optional.ofNullable(user.getUsername()),
                    Optional.ofNullable(user.getNickname()),
                    // Make sure to pass `nickname` if needed
                    null,
                    user.getProfile().getProfileImgUrl()
            );

        } catch (CustomException e) {
            return new SignInResponse(false, Optional.empty(), Optional.empty(), Optional.empty(),
                    null);

        }
//        if (userRepository.existsByUsername(username)) { //DB엔 String타입 저장되어있음
//            UserEntity userEntity = userRepository.findByUsername(signInRequest.username().toString());
//            String serverToken = jwtUtil.createJwt(userEntity.getUsername(), "USER",60*60*1000*10L);
//            return new SignInResponse(true, Optional.ofNullable(userEntity.getUsername()), Optional.ofNullable(userEntity.getNickname()),Optional.ofNullable(serverToken));
//        } else {
//            return new SignInResponse(false, Optional.empty(), Optional.empty(),Optional.empty());
//        }
    }

    @Transactional
    public SignInResponse kakaoSignInUser(
            KakaoSignInRequest kakaoSignInRequest) { //자동로그인 튕겼을때, 서버토큰 재발급
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + kakaoSignInRequest.kakaoAccessToken());
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me", //post요청보낼 주소
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );
        System.out.println(response);

        // JSON 문자열 파싱
        JsonObject rootObject = JsonParser.parseString(response.getBody()).getAsJsonObject();

        // id와 nickname 추출
        long id = rootObject.get("id").getAsLong();
        String username = Long.toString(id);

        if (userRepository.existsByUsername(username)) { //DB엔 String타입 저장되어있음
            User user = userRepository.findByUsername(username);
            String serverToken = jwtUtil.createJwt(user.getUsername(), "USER",
                    60 * 60 * 1000 * 10L);
            return new SignInResponse(true, Optional.ofNullable(user.getUsername()),
                    Optional.ofNullable(
                            user.getNickname()), Optional.ofNullable(serverToken),
                    user.getProfile().getProfileImgUrl());
        } else {
            return new SignInResponse(false, Optional.empty(), Optional.empty(), Optional.empty(),
                    null);
        }

    }


    @Transactional
    public SignUpResponse kakaoJoinUser(KakaoJoinRequest kakaoJoinRequest) { // 첫 회원가입 시 서버토큰 생성
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + kakaoJoinRequest.kakaoOauthToken());
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me", //post요청보낼 주소
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );
        System.out.println(response);

        // JSON 문자열 파싱
        JsonObject rootObject = JsonParser.parseString(response.getBody()).getAsJsonObject();

        // id와 nickname 추출
        long id = rootObject.get("id").getAsLong();
        String username = Long.toString(id);
        String nickname = rootObject.get("properties").getAsJsonObject().get(
                "nickname").getAsString();

        // 응답의 상태 코드 확인
        if (response.getStatusCode().is2xxSuccessful()) {
            User user = User.builder()
                    .username(username)
                    .nickname(kakaoJoinRequest.nickname())
                    .gender(kakaoJoinRequest.gender())
                    .age(kakaoJoinRequest.birth())
                    .created_at(LocalDateTime.now())
                    .build();
            userRepository.save(user);
            Profile profile = Profile.builder()
                    .user(user)
                    .build();
            profileRepository.save(profile);
            String serverToken = jwtUtil.createJwt(user.getUsername(), "USER", 60 * 1000L);
            return new SignUpResponse(user.getUsername(), user.getNickname(), serverToken);
        } else {
            throw new IllegalArgumentException("Invalid access token: " + response.getBody());
        }

    }

    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }
}

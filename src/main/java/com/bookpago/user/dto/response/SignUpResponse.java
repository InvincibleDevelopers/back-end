package invincibleDevs.bookpago.users.dto.response;

public record SignUpResponse(
        String username,
        String nickname,
        String serverToken
) {

}

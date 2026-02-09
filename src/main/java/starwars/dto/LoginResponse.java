package starwars.dto;

import lombok.Builder;

@Builder
public record LoginResponse(String accessToken, String refreshToken, UserInfo user) {
}

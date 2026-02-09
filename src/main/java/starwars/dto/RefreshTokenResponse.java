package starwars.dto;

import lombok.Builder;

@Builder
public record RefreshTokenResponse(String accessToken) {
}

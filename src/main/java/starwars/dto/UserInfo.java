package starwars.dto;

import lombok.Builder;

@Builder
public record UserInfo(String username) {
}

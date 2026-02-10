package starwars.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record PeopleResponse(
        Integer count, String next, String previous, List<CharacterResponse> results) {}

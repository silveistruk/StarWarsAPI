package starwars.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record SwapiPeopleResponse(
        Integer count, String next, String previous, List<SwapiCharacterResponse> results) {}

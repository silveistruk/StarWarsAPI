package starwars.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PeopleResponse(Integer count,
                             String next,
                             String previous,
                             List<SwapiCharacterResponse> results) {
}

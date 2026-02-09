package starwars.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record SwapiPeopleResponse(Integer count,
                                  String next,
                                  String previous,
                                  List<SwapiCharacterResponse> results) {

}

package starwars.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record SwapiCharacterResponse(String name,
                                     String height,
                                     String mass,
                                     @JsonProperty("hair_color") String hairColor,
                                     @JsonProperty("skin_color") String skinColor,
                                     @JsonProperty("eye_color") String eyeColor,
                                     @JsonProperty("birth_year") String birthYear,
                                     String gender,
                                     String homeworld,
                                     List<String> films,
                                     List<String> species,
                                     List<String> vehicles,
                                     List<String> starships,
                                     Instant created,
                                     Instant edited,
                                     String url) {

}

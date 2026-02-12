package starwars.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record CharacterResponse(
        @JsonProperty("name") String name,
        @JsonProperty("height") String height,
        @JsonProperty("mass") String mass,
        @JsonProperty("birth_year") String birthYear,
        @JsonProperty("number_of_films") Integer numberOfFilms,
        @JsonProperty("date_added") String dateAdded) {}

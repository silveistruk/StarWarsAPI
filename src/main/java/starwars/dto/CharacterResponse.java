package starwars.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record CharacterResponse(
        String name,
        String height,
        String mass,
        @JsonProperty("birth_year") String birthYear,
        @JsonProperty("number_of_films") Integer numberOfFilms,
        @JsonProperty("date_added") String dateAdded) {}

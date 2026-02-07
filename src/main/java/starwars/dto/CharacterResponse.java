package starwars.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CharacterResponse(String name,
                                String height,
                                String mass,
                                @JsonProperty("birth_year") String birthYear,
                                @JsonProperty("number_of_films") Integer numberOfFilms,
                                @JsonProperty("number_of_films") LocalDate dateAdded) {
}

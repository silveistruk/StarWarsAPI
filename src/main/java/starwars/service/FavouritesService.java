package starwars.service;

import org.springframework.stereotype.Service;
import starwars.dto.CharacterResponse;

import java.util.Arrays;
import java.util.List;

@Service
public class FavouritesService {
    public List<CharacterResponse> getFavourites(String username) {
        return Arrays.asList(
                CharacterResponse.builder()
                        .name("Luke Skywalker")
                        .height("1.72")
                        .mass("77")
                        .birthYear("19BBY")
                        .numberOfFilms(4)
                        .dateAdded("09-12-2014")
                        .build(),
                CharacterResponse.builder()
                        .name("Obi-Wan Kenobi")
                        .height("1.82")
                        .mass("77")
                        .birthYear("57BBY")
                        .numberOfFilms(6)
                        .dateAdded("10-12-2014")
                        .build(),
                CharacterResponse.builder()
                        .name("Han Solo")
                        .height("1.8")
                        .mass("80")
                        .birthYear("29BBY")
                        .numberOfFilms(3)
                        .dateAdded("10-12-2014")
                        .build());
    }
}

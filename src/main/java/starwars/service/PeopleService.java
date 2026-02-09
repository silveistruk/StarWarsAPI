package starwars.service;

import org.springframework.stereotype.Service;
import starwars.dto.CharacterResponse;
import starwars.dto.PeopleResponse;
import starwars.dto.SwapiCharacterResponse;
import starwars.dto.SwapiPeopleResponse;
import starwars.helper.CharacterInfoConverter;

import java.util.List;

@Service
public class PeopleService {

    private final ExternalSwapiService swapiService;

    public PeopleService(ExternalSwapiService swapiService) {
        this.swapiService = swapiService;
    }

    public PeopleResponse getPeople(Integer page) {
        if (page == null || page < 1) {
            throw new IllegalArgumentException("Page number must be positive. Given page number: " + page);
        }

        SwapiPeopleResponse swapiResponse = swapiService.getPeople(page);

        List<CharacterResponse> characters = swapiResponse.results()
                .stream()
                .map(CharacterInfoConverter::mapToCharacterResponse)
                .toList();
        return PeopleResponse.builder()
                .count(swapiResponse.count())
                .next(swapiResponse.next())
                .previous(swapiResponse.previous())
                .results(characters)
                .build();
    }

    public CharacterResponse getCharacterById(Integer id) {
        if(id == null || id < 1) {
            throw new IllegalArgumentException("Character id must be positive. Given character id: " + id);
        }

        SwapiCharacterResponse character = swapiService.getCharacterById(id);
        return CharacterInfoConverter.mapToCharacterResponse(character);
    }
}

package starwars.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import starwars.dto.CharacterResponse;
import starwars.dto.PeopleResponse;
import starwars.dto.SwapiCharacterResponse;
import starwars.dto.SwapiPeopleResponse;
import starwars.helper.CharacterInfoConverter;

import java.util.List;

@Service
public class PeopleService {
    private static final Logger logger = LoggerFactory.getLogger(PeopleService.class);

    private final ExternalSwapiService swapiService;

    public PeopleService(ExternalSwapiService swapiService) {
        this.swapiService = swapiService;
    }

    public PeopleResponse getPeople(Integer page) {
        logger.debug("Getting people list for page: {}", page);

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
        logger.debug("Getting person details for ID: {}", id);

        if (id == null || id < 1) {
            throw new IllegalArgumentException("Character id must be positive. Given character id: " + id);
        }

        SwapiCharacterResponse character = swapiService.getCharacterById(id);
        return CharacterInfoConverter.mapToCharacterResponse(character);
    }
}

package starwars.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import starwars.dto.SwapiCharacterResponse;
import starwars.dto.SwapiPeopleResponse;
import starwars.exception.CharacterNotFoundException;
import starwars.exception.ExternalApiException;
import starwars.exception.PageNotFoundException;

@Service
public class ExternalSwapiService {
    private static final Logger logger = LoggerFactory.getLogger(ExternalSwapiService.class);

    private final RestClient restClient;
    private final String peoplePath;
    private final String characterPath;

    public ExternalSwapiService(
            RestClient restClient,
            @Value("${swapi.path.people}") String peoplePath,
            @Value("${swapi.path.character}") String characterPath) {
        this.restClient = restClient;
        this.peoplePath = peoplePath;
        this.characterPath = characterPath;
    }

    // todo add caching
    public SwapiPeopleResponse getPeople(Integer page) {
        logger.info("Fetching people from SWAPI - page: {}", page);

        try {
            ResponseEntity<SwapiPeopleResponse> response =
                    restClient.get().uri(peoplePath + page).retrieve().toEntity(SwapiPeopleResponse.class);

            if (response.getStatusCode() == HttpStatus.OK && response.hasBody()) {
                logger.info("Successfully fetched page {} from SWAPI", page);
                return response.getBody();
            } else {
                throw new ExternalApiException("Bad response from SWAPI");
            }
        } catch (HttpClientErrorException.NotFound e) {
            logger.error("Page {} not found in SWAPI", page);
            throw new PageNotFoundException("Page not found: " + page);
        } catch (RestClientException e) {
            logger.error("Error communicating with SWAPI", e);
            throw new ExternalApiException("Failed to get people from SWAPI: " + e.getMessage(), e);
        }
    }

    // todo add caching
    public SwapiCharacterResponse getCharacterById(Integer id) {
        logger.info("Fetching person {} from SWAPI", id);

        try {
            ResponseEntity<SwapiCharacterResponse> response =
                    restClient.get().uri(characterPath, id).retrieve().toEntity(SwapiCharacterResponse.class);

            if (response.getStatusCode() == HttpStatus.OK && response.hasBody()) {
                logger.info("Successfully fetched person {} from SWAPI", id);
                return response.getBody();
            } else {
                throw new ExternalApiException("Bad response from SWAPI");
            }
        } catch (HttpClientErrorException.NotFound e) {
            logger.error("Person {} not found in SWAPI", id);
            throw new CharacterNotFoundException("Character not found: " + id);
        } catch (RestClientException e) {
            logger.error("Error communicating with SWAPI", e);
            throw new ExternalApiException("Failed to get character from SWAPI: " + e.getMessage(), e);
        }
    }
}

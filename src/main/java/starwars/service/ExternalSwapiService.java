package starwars.service;

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
    private final RestClient restClient;
    private final String peoplePath;
    private final String characterPath;

    public ExternalSwapiService(RestClient restClient,
                                @Value("$swapi.path.people") String peoplePath,
                                @Value("$swapi.path.character") String characterPath) {
        this.restClient = restClient;
        this.peoplePath = peoplePath;
        this.characterPath = characterPath;
    }

    // todo add logging
    // todo add caching
    public SwapiPeopleResponse getPeople(Integer page) {
        try {
            ResponseEntity<SwapiPeopleResponse> response = restClient
                    .get()
                    .uri(peoplePath + page)
                    .retrieve()
                    .toEntity(SwapiPeopleResponse.class);

            if (response.getStatusCode() == HttpStatus.OK && response.hasBody()) {
                return response.getBody();
            } else {
                throw new ExternalApiException("Bad response from SWAPI");
            }
        } catch (HttpClientErrorException.NotFound e) {
            throw new PageNotFoundException("Page not found: " + page);
        } catch (RestClientException e) {
            throw new ExternalApiException("Failed to get people from SWAPI: " + e.getMessage(), e);
        }
    }

    // todo add logging
    // todo add caching
    public SwapiCharacterResponse getCharacterById(Integer id) {
        try {
            ResponseEntity<SwapiCharacterResponse> response = restClient
                    .get()
                    .uri(characterPath, id)
                    .retrieve()
                    .toEntity(SwapiCharacterResponse.class);

            if (response.getStatusCode() == HttpStatus.OK && response.hasBody()) {
                return response.getBody();
            } else {
                throw new ExternalApiException("Bad response from SWAPI");
            }
        } catch (HttpClientErrorException.NotFound e) {
            throw new CharacterNotFoundException("Character not found: " + id);
        } catch (RestClientException e) {
            throw new ExternalApiException("Failed to get character from SWAPI: " + e.getMessage(), e);
        }
    }
}

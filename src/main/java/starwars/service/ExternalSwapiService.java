package starwars.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import starwars.dto.SwapiCharacterResponse;
import starwars.dto.SwapiPeopleResponse;

@Service
public class ExternalSwapiService {
    private final RestTemplate restTemplate;
    private final String swapiUrl;

    public ExternalSwapiService(RestTemplate restTemplate,
                                String swapiUrl) {
        this.restTemplate = restTemplate;
        this.swapiUrl = swapiUrl;
    }

    public SwapiPeopleResponse getPeople(Integer page) {
        return null;
    }

    public SwapiCharacterResponse getCharacterById(Integer id) {
        return null;
    }
}

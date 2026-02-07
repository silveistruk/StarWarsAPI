package starwars.service;

public class CharacterService {

    private final ExternalSwapiService swapiService;

    public CharacterService(ExternalSwapiService swapiService) {
        this.swapiService = swapiService;
    }
}

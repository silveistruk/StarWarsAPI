package starwars.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import starwars.dto.SwapiPeopleResponse;
import starwars.service.CharacterService;


@RestController
@RequestMapping("/people")
public class PeopleController {

    private final CharacterService characterService;

    public PeopleController(CharacterService characterService) {
        this.characterService = characterService;
    }

    public ResponseEntity<SwapiPeopleResponse> getPeople(@RequestParam(defaultValue = "1") Integer page) {
        SwapiPeopleResponse response = SwapiPeopleResponse.builder().build();
        return ResponseEntity.ok(response);
    }



}

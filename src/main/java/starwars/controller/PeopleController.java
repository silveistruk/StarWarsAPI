package starwars.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import starwars.dto.CharacterResponse;
import starwars.dto.PeopleResponse;
import starwars.service.PeopleService;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;

    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
    public ResponseEntity<PeopleResponse> getPeople(@RequestParam(defaultValue = "1") Integer page) {
        PeopleResponse response = peopleService.getPeople(page);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterResponse> getCharacterById(@PathVariable Integer id) {
        CharacterResponse response = peopleService.getCharacterById(id);
        return ResponseEntity.ok(response);
    }
}

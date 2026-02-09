package starwars.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import starwars.dto.CharacterResponse;
import starwars.dto.PeopleResponse;
import starwars.service.PeopleService;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private static final Logger logger = LoggerFactory.getLogger(PeopleController.class);
    private final PeopleService peopleService;

    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
    public ResponseEntity<PeopleResponse> getPeople(@RequestParam(defaultValue = "1") Integer page) {
        logger.info("GET /people - page: {}", page);
        PeopleResponse response = peopleService.getPeople(page);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterResponse> getCharacterById(@PathVariable Integer id) {
        logger.info("GET /people/{} - fetching character details", id);
        CharacterResponse response = peopleService.getCharacterById(id);
        return ResponseEntity.ok(response);
    }
}

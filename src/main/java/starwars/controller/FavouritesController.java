package starwars.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import starwars.dto.CharacterResponse;
import starwars.service.FavouritesService;

import java.util.List;

@RestController
@RequestMapping("/favourites")
public class FavouritesController {

    private final FavouritesService favouritesService;

    public FavouritesController(FavouritesService favouritesService) {
        this.favouritesService = favouritesService;
    }

    @GetMapping
    public ResponseEntity<List<CharacterResponse>> getFavourites(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        List<CharacterResponse> response = favouritesService.getFavourites(username);
        return ResponseEntity.ok(response);
    }
}

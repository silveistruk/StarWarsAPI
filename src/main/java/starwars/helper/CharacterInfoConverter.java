package starwars.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import starwars.dto.CharacterResponse;
import starwars.dto.SwapiCharacterResponse;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

public class CharacterInfoConverter {
    private static final Logger logger = LoggerFactory.getLogger(CharacterInfoConverter.class);

    private static final Pattern NUMBER_PATTERN = Pattern
            .compile("^-?\\d+(\\.\\d+)?$");

    private static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter
            .ofPattern("dd-MM-yyyy")
            .withZone(ZoneOffset.UTC);

    private static final String UNKNOWN = "unknown";

    private CharacterInfoConverter() {
    }

    public static CharacterResponse mapToCharacterResponse(SwapiCharacterResponse character) {
        return CharacterResponse.builder()
                .name(character.name())
                .height(convertToMeters(character.height()))
                .mass(convertToKg(character.mass()))
                .numberOfFilms(getNumberOfFilms(character.films()))
                .birthYear(character.birthYear())
                .dateAdded(formatDate(character.created()))
                .build();
    }

    private static String convertToMeters(String height) {
        if (height == null || height.equalsIgnoreCase(UNKNOWN) || !isNumeric(height)) {
            return UNKNOWN;
        }
        try {
            double heightMeters = Double.parseDouble(height) / 100;
            return String.format("%.2f", heightMeters);
        } catch (NumberFormatException e) {
            logger.warn("Could not parse height: {}", height);
            return UNKNOWN;
        }
    }

    private static String convertToKg(String mass) {
        if (mass == null || mass.equalsIgnoreCase(UNKNOWN)) {
            return UNKNOWN;
        }
        mass = mass.replace(",", "");
        return isNumeric(mass) ? mass : UNKNOWN;
    }

    private static Integer getNumberOfFilms(List<String> films) {
        return films != null ? films.size() : 0;
    }

    private static String formatDate(Instant date) {
        if (date == null) {
            return UNKNOWN;
        }
        return DATE_PATTERN.format(date);
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return NUMBER_PATTERN.matcher(strNum).matches();
    }
}

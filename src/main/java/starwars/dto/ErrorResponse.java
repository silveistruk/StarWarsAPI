package starwars.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
public record ErrorResponse(LocalDateTime dateTime,
                            Integer status,
                            String error,
                            String message,
                            Map<String, String> details) {
}

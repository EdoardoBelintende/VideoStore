package it.cgmconsulting.Belintende.payload.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class FilmRequest {

    @NotEmpty @Size(max = 100, min = 1)
    private String title;
    private String description;
    @Min(1895)
    private short releaseYear;
    @Min(1)
    private long languageId;
    @Min(1)
    private long genreId;
}

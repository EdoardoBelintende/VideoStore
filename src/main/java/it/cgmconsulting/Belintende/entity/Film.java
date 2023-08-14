package it.cgmconsulting.Belintende.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long filmId;
    @Column(length = 100, nullable = false)
    private String title;
    @Column(length = 20000, nullable = false)
    private String description;
    @Column(nullable = false)
    private short releaseYear;
    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language languageId = null;
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genreId = null;

    public Film(long filmId) {
        this.filmId = filmId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return filmId == film.filmId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId);
    }
}

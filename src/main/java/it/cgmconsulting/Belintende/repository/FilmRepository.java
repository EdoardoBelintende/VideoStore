package it.cgmconsulting.Belintende.repository;

import it.cgmconsulting.Belintende.entity.Film;
import it.cgmconsulting.Belintende.payload.response.FilmResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    boolean existsByTitle(String title);

    @Query(value = "SELECT new it.cgmconsulting.Belintende.payload.response.FilmResponse(" +
            "f.filmId, " +
            "f.title, " +
            "f.description, " +
            "f.releaseYear, " +
            "f.languageId.languageName) " +
            "FROM Film f " +
            "WHERE (f.languageId.languageId = :languageId)")
    List<FilmResponse> findFilmsByLanguageId(@Param("languageId") long languageId);





}

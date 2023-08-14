package it.cgmconsulting.Belintende.service;

import it.cgmconsulting.Belintende.entity.*;
import it.cgmconsulting.Belintende.payload.request.FilmRequest;
import it.cgmconsulting.Belintende.payload.response.*;
import it.cgmconsulting.Belintende.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmRepository filmRepository;
    private final RentalRepository rentalRepository;
    private final FilmStaffRepository filmStaffRepository;
    private final LanguageRepository languageRepository;
    private final GenreRepository genreRepository;

    //1.
    @Transactional
    public ResponseEntity<?> updateFilm(FilmRequest request, long filmId) {
        if(request.getReleaseYear() > LocalDateTime.now().getYear())
            return new ResponseEntity<>("The release year is in the future", HttpStatus.BAD_REQUEST);

        Optional<Film> film = filmRepository.findById(filmId);
        if(film.isEmpty())
            return new ResponseEntity<>("Film not found", HttpStatus.NOT_FOUND);
        film.get().setTitle(request.getTitle().toUpperCase());
        film.get().setDescription(request.getDescription());
        film.get().setReleaseYear(request.getReleaseYear());

        Optional<Language> language = languageRepository.findById(request.getLanguageId());
        if (language.isEmpty()){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity<>("Language not exists", HttpStatus.NOT_FOUND);
        }
        film.get().setLanguageId(new Language(request.getLanguageId()));
        
        Optional<Genre> genre = genreRepository.findById(request.getGenreId());
        if (genre.isEmpty()) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity<>("Genre not exists", HttpStatus.NOT_FOUND);
        }
        film.get().setGenreId(new Genre(request.getGenreId()));

        return new ResponseEntity<>("Film has been updated", HttpStatus.OK);
    }

    //2.
    public ResponseEntity<?> findFilmsByLanguageId(long languageId) {
        List<FilmResponse> list = filmRepository.findFilmsByLanguageId(languageId);
        if(list.isEmpty())
            return new ResponseEntity<>("Film in language "+languageId+" not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //9.
    public ResponseEntity<?> findFilmsByActors(List<Long> staffIds) {
        if(staffIds.isEmpty())
            return new ResponseEntity<>("No staff selected", HttpStatus.BAD_REQUEST);

        List<Long> actorsIds = filmStaffRepository.getActorsIds(staffIds);
        // Se il numero di elementi di actorIds è diverso da quello di staffIds significa che non sono tutti ACTOR
        if(staffIds.size() != actorsIds.size())
            return new ResponseEntity<>("You not selected only actors", HttpStatus.BAD_REQUEST);

        List<FilmResponse> filmsByStaffIds = filmStaffRepository.getFilmsByStaffIds(actorsIds, Long.valueOf(actorsIds.size()));

        if(filmsByStaffIds.isEmpty())
            return new ResponseEntity<>("No film found with ALL the selected actors", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(filmsByStaffIds, HttpStatus.OK);
    }

    //10.
    public ResponseEntity<?> findRentableFilms(String title) {
        List<FilmRentableResponse> list = rentalRepository.getRentableFilmsByTitle(title.toUpperCase());
        if (list.isEmpty())
            return new ResponseEntity<>("Film not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}

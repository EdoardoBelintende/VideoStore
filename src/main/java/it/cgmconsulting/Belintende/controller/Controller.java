package it.cgmconsulting.Belintende.controller;

import it.cgmconsulting.Belintende.payload.request.FilmRequest;
import it.cgmconsulting.Belintende.payload.request.RentalRequest;
import it.cgmconsulting.Belintende.service.FilmService;
import it.cgmconsulting.Belintende.service.InventoryService;
import it.cgmconsulting.Belintende.service.RentalService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Validated
public class Controller {

    private final FilmService filmService;
    private final InventoryService inventoryService;
    private final RentalService rentalService;

    //1. localhost:8082/update-film/{filmId}
    @PatchMapping("/update-film/{filmId}")
    public ResponseEntity<?> updateFilm(@RequestBody @Valid FilmRequest request, @PathVariable @Min(1) long filmId){
        return filmService.updateFilm(request, filmId);
    }

    //2. localhost:8082/find-films-by-language/{languageId}
    @GetMapping("/find-films-by-language/{languageId}")
    public ResponseEntity<?> findFilmsByLanguageId(@PathVariable @Min(1) long languageId){
        return filmService.findFilmsByLanguageId(languageId);
    }

    //3. localhost:8082/add-film-to-store/{storeId}/{filmId}
    @PutMapping("/add-film-to-store/{storeId}/{filmId}")
    public ResponseEntity<?> addFilmToStore(@PathVariable @Min(1) long storeId, @PathVariable @Min(1) long filmId){
        return inventoryService.addFilmToStore(storeId, filmId);
    }

    //4. localhost:8082/count-customers-by-store/{storeName}
    @GetMapping("/count-customers-by-store/{storeName}")
    public ResponseEntity<?> countCustomersByStore(@PathVariable String storeName){
        return rentalService.countCustomersByStore(storeName);
    }

    //5. localhost:8082/add-update-rental
    @PutMapping("/add-update-rental")
    public ResponseEntity<?> addUpdateRental(@RequestBody @Valid RentalRequest request){
        return rentalService.addUpdateRental(request);
    }

    //6. localhost:8082/count-rentals-in-date-range-by-store/{storeId}
    @GetMapping("/count-rentals-in-date-range-by-store/{storeId}")
    public ResponseEntity<?> countRentalsByStore(@PathVariable @Min(1) long storeId,
                                                 @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
                                                 @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        return rentalService.countRentalsByStore(storeId, start, end);
    }

    //7. localhost:8082/find-all-films-rent-by-one-customer/{customerId}
    @GetMapping("/find-all-films-rent-by-one-customer/{customerId}")
    public ResponseEntity<?> findFilmsRentByCustomer(@PathVariable @Min(1) long customerId){
        return rentalService.findFilmsRentByCustomer(customerId);
    }

    //8. localhost:8082/find-film-with-max-number-of-rent
    @GetMapping("/find-film-with-max-number-of-rent")
    public ResponseEntity<?> findFilmMaxRent(){
        return rentalService.findFilmMaxRent();
    }

    //9. localhost:8082/find-films-by-actors
    @GetMapping("/find-films-by-actors")
    public ResponseEntity<?> findFilmsByActors(@RequestParam List<Long> staffIds){
        return filmService.findFilmsByActors(staffIds);
    }

    //10. localhost:8082/find-rentable-films
    @GetMapping("/find-rentable-films")
    public ResponseEntity<?> findRentableFilms(@RequestParam String title){
        return filmService.findRentableFilms(title);
    }
}

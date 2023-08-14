package it.cgmconsulting.Belintende.service;

import it.cgmconsulting.Belintende.entity.Film;
import it.cgmconsulting.Belintende.entity.Inventory;
import it.cgmconsulting.Belintende.entity.Store;
import it.cgmconsulting.Belintende.repository.FilmRepository;
import it.cgmconsulting.Belintende.repository.InventoryRepository;
import it.cgmconsulting.Belintende.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final StoreRepository storeRepository;
    private final FilmRepository filmRepository;
    private final InventoryRepository inventoryRepository;

    //3.
    public ResponseEntity<?> addFilmToStore(long storeId, long filmId) {
        if (!storeRepository.existsById(storeId)) {
            return new ResponseEntity<>("Store with id " + storeId + " not found", HttpStatus.NOT_FOUND);
        }else if(!filmRepository.existsById(filmId)){
            return new ResponseEntity<>("Film with id " + filmId + " not found", HttpStatus.NOT_FOUND);
        }

        Inventory i = new Inventory(new Store(storeId), new Film(filmId));
        inventoryRepository.save(i);
        return new ResponseEntity<>("Film " + filmId + " has been added to the store " + storeId, HttpStatus.OK);
    }
}

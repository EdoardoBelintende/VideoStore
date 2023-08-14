package it.cgmconsulting.Belintende.service;

import it.cgmconsulting.Belintende.entity.Customer;
import it.cgmconsulting.Belintende.entity.Inventory;
import it.cgmconsulting.Belintende.entity.Rental;
import it.cgmconsulting.Belintende.entity.RentalId;
import it.cgmconsulting.Belintende.payload.request.RentalRequest;
import it.cgmconsulting.Belintende.payload.response.CustomerResponse;
import it.cgmconsulting.Belintende.payload.response.FilmMaxRentResponse;
import it.cgmconsulting.Belintende.payload.response.FilmRentResponse;
import it.cgmconsulting.Belintende.repository.CustomerRepository;
import it.cgmconsulting.Belintende.repository.InventoryRepository;
import it.cgmconsulting.Belintende.repository.RentalRepository;
import it.cgmconsulting.Belintende.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final InventoryRepository inventoryRepository;
    private final CustomerRepository customerRepository;
    private final StoreRepository storeRepository;

    //4.
    public ResponseEntity<?> countCustomersByStore(String storeName) {
        if(!storeRepository.existsByStoreName(storeName.toUpperCase()))
            return new ResponseEntity<>("Store " + storeName + " not found", HttpStatus.NOT_FOUND);
        CustomerResponse c = rentalRepository.getCustomersByStoreName(storeName.toUpperCase());
        return new ResponseEntity<>(c, HttpStatus.OK);
    }

    //5.
    @Transactional
    public ResponseEntity<?> addUpdateRental(RentalRequest request) {
        Optional<Inventory> i = inventoryRepository.findById(request.getInventoryId());
        if (i.isEmpty())
            return new ResponseEntity<>("Film not found in store", HttpStatus.NOT_FOUND);

        if (!customerRepository.existsById(request.getCustomerId()))
            return new ResponseEntity<>("Customer with id " + request.getCustomerId() + " not found", HttpStatus.NOT_FOUND);

        Optional<Rental> rental = rentalRepository.getRentalByInventory(request.getInventoryId());

        // Se trovo il noleggio allora significa che posso solo restituirlo
        if (rental.isPresent()){
            if(rental.get().getRentalId().getCustomerId().getCustomerId() == request.getCustomerId()) {
                rental.get().setRentalReturn(LocalDateTime.now());
                return new ResponseEntity<>("Film was returned", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Ths film is in rental", HttpStatus.BAD_REQUEST);
            }
        }
        // altrimenti procedo al noleggio
        Rental newRental = new Rental(new RentalId(new Customer(request.getCustomerId()), i.get(), LocalDateTime.now()));
        rentalRepository.save(newRental);
        return new ResponseEntity<>("The film was rented", HttpStatus.OK);
    }

    //6.
    public ResponseEntity<?> countRentalsByStore(long storeId, LocalDate start, LocalDate end) {
        LocalDateTime startDate = start.atStartOfDay();
        LocalDateTime endDate = end.atTime(23, 59);

        if (start.isAfter(end))
            return new ResponseEntity<>("Start date should be before the end date", HttpStatus.BAD_REQUEST);

        if (!storeRepository.existsById(storeId))
            return new ResponseEntity<>("Store with id " + storeId + " not found", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(rentalRepository.countRentalsInDateRangeByStore(storeId, startDate, endDate), HttpStatus.OK);
    }

    //7.
    public ResponseEntity<?> findFilmsRentByCustomer(long customerId) {
        if (!customerRepository.existsById(customerId))
            return new ResponseEntity<>("Customer with id " + customerId + " not found", HttpStatus.NOT_FOUND);
        List<FilmRentResponse> list = rentalRepository.findFilmsRentByCustomer(customerId);
        if (list.isEmpty())
            return new ResponseEntity<>("No rents found for customer "+customerId, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //8.
    public ResponseEntity<?> findFilmMaxRent() {
        List<FilmMaxRentResponse> list = rentalRepository.findFilmMaxRent();
        List<FilmMaxRentResponse> result = new ArrayList<>();

        long maxRent = list.get(0).getTotNoleggi();
        for(FilmMaxRentResponse f : list){
            if(maxRent == f.getTotNoleggi())
                result.add(f);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

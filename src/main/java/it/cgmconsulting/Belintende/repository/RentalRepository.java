package it.cgmconsulting.Belintende.repository;

import it.cgmconsulting.Belintende.entity.Rental;
import it.cgmconsulting.Belintende.entity.RentalId;
import it.cgmconsulting.Belintende.payload.response.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rental, RentalId> {


    @Query("SELECT new it.cgmconsulting.Belintende.payload.response.CustomerResponse(" +
            "s.storeName, " +
            "COUNT(DISTINCT r.rentalId.customerId.customerId)" +
            ") " +
            "FROM Store s " +
            "LEFT JOIN Inventory i ON i.storeId.storeId = s.storeId " +
            "LEFT JOIN Rental r ON i.inventoryId = r.rentalId.inventoryId.inventoryId " +
            "LEFT JOIN Customer c ON c.customerId = r.rentalId.customerId.customerId " +
            "WHERE s.storeName = :storeName")
    CustomerResponse getCustomersByStoreName(@Param("storeName") String storeName);

    @Query("SELECT r FROM Rental r " +
            "WHERE r.rentalId.inventoryId.inventoryId = :inventoryId " +
            "AND r.rentalReturn IS NULL ")
    Optional<Rental> getRentalByInventory(@Param("inventoryId") long inventoryId);

    @Query("SELECT COUNT(r) FROM Rental r " +
            "WHERE r.rentalId.inventoryId.storeId.storeId = :storeId " +
            "AND (r.rentalId.rentalDate BETWEEN :start AND :end)")
    Long countRentalsInDateRangeByStore(@Param("storeId") long storeId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query(value = "SELECT new it.cgmconsulting.Belintende.payload.response.FilmRentResponse(" +
            "r.rentalId.inventoryId.filmId.filmId, " +
            "r.rentalId.inventoryId.filmId.title, " +
            "r.rentalId.inventoryId.storeId.storeName) " +
            "FROM Rental r " +
            "WHERE r.rentalId.customerId.customerId = :customerId"
    )
    List<FilmRentResponse> findFilmsRentByCustomer(@Param("customerId") long customerId);

    @Query(value = "SELECT new it.cgmconsulting.Belintende.payload.response.FilmMaxRentResponse(" +
            "r.rentalId.inventoryId.filmId.filmId, " +
            "r.rentalId.inventoryId.filmId.title, " +
            "COUNT(r.rentalId.inventoryId.filmId.filmId) AS totNoleggi) " +
            "FROM Rental r " +
            "GROUP BY r.rentalId.inventoryId.filmId.filmId, " +
            "r.rentalId.inventoryId.filmId.title " +
            "ORDER BY totNoleggi DESC"
    )
    List<FilmMaxRentResponse> findFilmMaxRent();

    @Query(value = "SELECT new it.cgmconsulting.Belintende.payload.response.FilmRentableResponse( " +
            "f.title, " +
            "s.storeName, "+
            "COUNT(DISTINCT(i.inventoryId)) AS numeroTotaleDiCopieInNegozio, " +
            "(COUNT(DISTINCT(i.inventoryId)) - SUM(CASE WHEN r.rentalReturn IS NULL AND r.rentalId.rentalDate IS NOT NULL THEN 1 ELSE 0 END))  AS numeroDiCopieDisponibiliInNegozio " +
            ") FROM Film f " +
            "INNER JOIN Inventory i ON i.filmId.filmId = f.filmId " +
            "INNER JOIN Store s  ON s.storeId = i.storeId.storeId " +
            "LEFT JOIN Rental r ON r.rentalId.inventoryId.inventoryId = i.inventoryId " +
            "WHERE f.title = :title AND (r.rentalReturn IS NULL OR (r.rentalId.rentalDate IS NOT NULL AND r.rentalReturn IS NOT NULL)) " +
            "GROUP BY f.title, s.storeName "
    )
    List<FilmRentableResponse> getRentableFilmsByTitle(@Param("title") String title);
}

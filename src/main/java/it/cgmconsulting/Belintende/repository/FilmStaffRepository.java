package it.cgmconsulting.Belintende.repository;

import it.cgmconsulting.Belintende.entity.FilmStaff;
import it.cgmconsulting.Belintende.entity.FilmStaffId;
import it.cgmconsulting.Belintende.payload.response.FilmResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmStaffRepository extends JpaRepository<FilmStaff, FilmStaffId> {

    @Query(value = "SELECT new it.cgmconsulting.Belintende.payload.response.FilmResponse(" +
            "f.filmId, " +
            "f.title, " +
            "f.description, " +
            "f.releaseYear, " +
            "l.languageName) " +
            "FROM Film f " +
            "INNER JOIN Language l ON f.languageId.languageId = l.languageId " +
            "INNER JOIN FilmStaff fs ON f.filmId = fs.filmStaffId.filmId.filmId " +
            "INNER JOIN Staff s ON fs.filmStaffId.staffId.staffId = s.staffId " +
            "INNER JOIN Role r ON fs.filmStaffId.roleId.roleId = r.roleId " +
            "WHERE s.staffId IN (:staffIds) " +
            "AND r.roleName = 'ACTOR' " +
            "GROUP BY f.filmId, f.title, f.description, f.releaseYear, l.languageName " +
            "HAVING COUNT(s.staffId) = :countActors")
    List<FilmResponse> getFilmsByStaffIds(List<Long> staffIds, long countActors);

    @Query(value="SELECT DISTINCT(fs.staff_id) " +
            "FROM staff s " +
            "INNER JOIN film_staff fs ON fs.staff_id=s.staff_id " +
            "INNER JOIN role r ON r.role_id=fs.role_id " +
            "WHERE s.staff_id IN(:staffIds) AND r.role_name = 'ACTOR'", nativeQuery = true)
    List<Long> getActorsIds(List<Long> staffIds);
}

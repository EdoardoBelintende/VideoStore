package it.cgmconsulting.Belintende.repository;

import it.cgmconsulting.Belintende.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}

package it.cgmconsulting.Belintende.repository;

import it.cgmconsulting.Belintende.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
}

package it.cgmconsulting.Belintende.repository;

import it.cgmconsulting.Belintende.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    boolean existsById(long storeId);

    boolean existsByStoreName(String storeName);

}

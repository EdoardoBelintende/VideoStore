package it.cgmconsulting.Belintende.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long inventoryId;
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store storeId;
    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false)
    private Film filmId;

    public Inventory(Store storeId, Film filmId) {
        this.storeId = storeId;
        this.filmId = filmId;
    }

    public Inventory(long inventoryId) {
        this.inventoryId = inventoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inventory inventory = (Inventory) o;
        return inventoryId == inventory.inventoryId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventoryId);
    }
}

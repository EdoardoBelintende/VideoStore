package it.cgmconsulting.Belintende.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class RentalId {

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customerId;
    @ManyToOne
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventoryId;

    @Column(nullable = false)
    private LocalDateTime rentalDate;
}

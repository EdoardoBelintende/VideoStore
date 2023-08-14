package it.cgmconsulting.Belintende.payload.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RentalRequest {

    @Min(1)
    private long customerId;
    @Min(1)
    private long inventoryId;
}

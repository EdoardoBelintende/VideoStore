package it.cgmconsulting.Belintende.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class FilmStaff {

    @EmbeddedId
    private FilmStaffId filmStaffId;

}

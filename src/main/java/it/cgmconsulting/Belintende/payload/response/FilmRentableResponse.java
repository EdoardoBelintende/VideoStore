package it.cgmconsulting.Belintende.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class FilmRentableResponse {

    private String title;
    private String storeName;
    private long numCopyStore; //numero totale di copie in possesso del negozio
    private long numCopyRentable; //numero di copie disponibili
}

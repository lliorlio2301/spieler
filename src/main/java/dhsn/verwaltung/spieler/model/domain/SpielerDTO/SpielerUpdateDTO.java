package dhsn.verwaltung.spieler.model.domain.SpielerDTO;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.format.annotation.DateTimeFormat;

import dhsn.verwaltung.spieler.model.domain.Position;
import dhsn.verwaltung.spieler.model.domain.Spieler;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpielerUpdateDTO {
    
    @Valid

    private Long id;
    
    @NotBlank(message = "Dieses Feld darf nicht leer sein")
    private String vorname;

    @NotBlank(message = "Dieses Feld darf nicht leer sein")
    private String nachname;

    @Size(min = 5, max = 100, message = "Password muss zwischen 5 & 15 lang sein")
    private String passwort;

    // Für HTML-input Feld
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Dieses Feld darf nicht leer sein")
    @Past(message = "Das Geburtsdatum muss in  der Vergangeheit liegen") 
    private LocalDate geburtsdatum;

    @NotNull(message = "Dieses Feld darf nicht leer sein")
    private Position position;
    
    @Column(unique = true)
    @NotNull(message = "Dieses Feld darf nicht leer sein")
    private int rueckennummer;

    private boolean willGehaltsErhoeung;
    
    public boolean isWillGehaltsErhoeung() {
        return willGehaltsErhoeung;
    }

    public void setWillGehaltsErhoeung(boolean willGehaltsErhoeung) {
        this.willGehaltsErhoeung = willGehaltsErhoeung;
    }

    public SpielerUpdateDTO(){}

    public SpielerUpdateDTO(Spieler s) {
        this.id = s.getId();
        this.vorname = s.getVorname();
        this.nachname = s.getNachname();
        this.geburtsdatum = s.getGeburtsdatum();
        this.position = s.getPosition();
        this.rueckennummer = s.getRueckennummer();
    }

    public int getAlter() {
        return Period.between(geburtsdatum, LocalDate.now()).getYears(); 
    }

}

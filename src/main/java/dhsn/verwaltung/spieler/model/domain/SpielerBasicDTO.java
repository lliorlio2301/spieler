package dhsn.verwaltung.spieler.model.domain;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class SpielerBasicDTO {
    
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
    
    public SpielerBasicDTO(){}

    public SpielerBasicDTO(Spieler s) {
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

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public LocalDate getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(LocalDate geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getRueckennummer() {
        return rueckennummer;
    }

    public void setRueckennummer(int rueckennummer) {
        this.rueckennummer = rueckennummer;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }



    public String getPasswort() {
        return passwort;
    }



    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

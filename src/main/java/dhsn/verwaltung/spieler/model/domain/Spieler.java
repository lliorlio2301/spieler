package dhsn.verwaltung.spieler.model.domain;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.format.annotation.DateTimeFormat;

import dhsn.verwaltung.spieler.model.identity.Benutzer;
import dhsn.verwaltung.spieler.model.identity.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

@Entity
@Table(name = "spieler")
//Referenz zu Benutzer Tabelle - Es ist FK & PK - Kein ID-Feld notwendig
@PrimaryKeyJoinColumn(name = "benutzer_id") 
public class Spieler extends Benutzer{

    @Valid

    @NotBlank(message = "Dieses Feld darf nicht leer sein")
    private String vorname;

    @NotBlank(message = "Dieses Feld darf nicht leer sein")
    private String nachname;
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
    
    public Spieler(){}

    

    public Spieler(String username, String password, Role role,
        String vorname, String nachname, LocalDate geburtsdatum, 
        Position position, int rueckennummer) {
        super(username, password, role);
        this.vorname = vorname;
        this.nachname = nachname;
        this.geburtsdatum = geburtsdatum;
        this.position = position;
        this.rueckennummer = rueckennummer;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public Integer getAlter() {
        return Period.between(geburtsdatum, LocalDate.now()).getYears();
    }

    public LocalDate getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(LocalDate geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public int getRueckennummer() {
        return rueckennummer;
    }

    public void setRueckennummer(int rueckennummer) {
        this.rueckennummer = rueckennummer;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}

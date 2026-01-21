package dhsn.verwaltung.spieler.model.domain;

import java.time.LocalDate;
import java.time.Period;

import dhsn.verwaltung.spieler.model.identity.Benutzer;
import dhsn.verwaltung.spieler.model.identity.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;


@Entity
@Table(name = "spieler")
//Referenz zu Benutzer Tabelle - Es ist FK & PK - Kein ID-Feld notwendig
@PrimaryKeyJoinColumn(name = "benutzer_id") 
public class Spieler extends Benutzer{

    private String vorname;
    
    private String nachname;
    // Für HTML-input Feld

    private LocalDate geburtsdatum;


    private Position position;

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

    public Spieler(String username, String passwort, Role role) {        
        super(username, passwort, role);
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

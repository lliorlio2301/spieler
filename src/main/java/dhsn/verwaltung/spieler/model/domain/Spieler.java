package dhsn.verwaltung.spieler.model.domain;

import java.time.LocalDate;

import dhsn.verwaltung.spieler.model.identity.Benutzer;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "spieler")
//Referenz zu Benutzer Tabelle - Es ist FK & PK - Kein ID-Feld notwendig
@PrimaryKeyJoinColumn(name = "benutzer_id") 
public class Spieler extends Benutzer{

    private String vorname;
    private String nachname;
    private Integer alter;
    private LocalDate geburtsdatum;

    private int rueckennummer;
    
    public Spieler(){}

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
        return alter;
    }

    public void setAlter(Integer alter) {
        this.alter = alter;
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

    
    

}

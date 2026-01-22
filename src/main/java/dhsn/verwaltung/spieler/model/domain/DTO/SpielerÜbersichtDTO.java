package dhsn.verwaltung.spieler.model.domain.DTO;

import java.time.LocalDate;
import java.time.Period;

import dhsn.verwaltung.spieler.model.domain.Position;


public class SpielerÜbersichtDTO {
    private String vorname;

    private String nachname;
    // Für HTML-input Feld

    private LocalDate geburtsdatum;
    
    private Position position;

    private int rueckennummer;

    private boolean willGehaltsErhoeung;
    
    public SpielerÜbersichtDTO(){}

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

    public boolean isWillGehaltsErhoeung() {
        return willGehaltsErhoeung;
    }

    public void setWillGehaltsErhoeung(boolean kannspielen) {
        this.willGehaltsErhoeung = kannspielen;
    }
}

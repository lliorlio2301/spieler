package dhsn.verwaltung.spieler.model.domain;

import java.time.LocalDate;
import java.time.Period;

import dhsn.verwaltung.spieler.model.identity.Benutzer;
import dhsn.verwaltung.spieler.model.identity.Role;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;


@Entity
@Table(name = "spieler")
//Referenz zu Benutzer Tabelle - Es ist FK & PK - Kein ID-Feld notwendig
@PrimaryKeyJoinColumn(name = "benutzer_id") 
public class Spieler extends Benutzer{

    private String vorname;

    private String nachname;

    private LocalDate geburtsdatum;
    
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "verein_id", referencedColumnName = "id")
    private Verein verein;

    private int rueckennummer;

    @Nullable
    private boolean willGehaltsErhoeung;
    
    public Spieler(){}

    public Spieler(String username, String password, Role role,
        String vorname, String nachname, LocalDate geburtsdatum, 
        Position position, int rueckennummer, Verein verein) {
        super(username, password, role);
        this.vorname = vorname;
        this.nachname = nachname;
        this.geburtsdatum = geburtsdatum;
        this.position = position;
        this.rueckennummer = rueckennummer;
        this.verein = verein;
    }

    public Spieler(String username, String password, Role role,
        String vorname, String nachname, LocalDate geburtsdatum, 
        Position position) {
        super(username, password, role);
        this.vorname = vorname;
        this.nachname = nachname;
        this.geburtsdatum = geburtsdatum;
        this.position = position;
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

    public boolean isWillGehaltsErhoeung() {
        return willGehaltsErhoeung;
    }

    public void setWillGehaltsErhoeung(boolean kannspielen) {
        this.willGehaltsErhoeung = kannspielen;
    }

    public Verein getVerein() {
        return verein;
    }

    public void setVerein(Verein verein) {
        this.verein = verein;
    }
}

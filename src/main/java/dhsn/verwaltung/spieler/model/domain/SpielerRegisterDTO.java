package dhsn.verwaltung.spieler.model.domain;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.format.annotation.DateTimeFormat;

import dhsn.verwaltung.spieler.model.identity.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class SpielerRegisterDTO {

    @Valid

    @NotBlank(message = "Username muss eingetragen werden")
    private String username;
    
    @NotBlank(message = "Password muss eingetragen werden")
    @Size(min = 5, max = 100, message = "Password muss zwischen 5 & 15 lang sein")
    private String passwort;

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

    @NotNull(message = "Eine Rolle muss eingetragen werden")
    @Enumerated(EnumType.STRING)
    private Role role;
    
    public SpielerRegisterDTO(){}   

    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
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


    public Role getRole() {
        return role;
    }


    public void setRole(Role role) {
        this.role = role;
    }
}

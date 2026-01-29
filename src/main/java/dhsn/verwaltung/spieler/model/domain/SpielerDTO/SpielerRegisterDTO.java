package dhsn.verwaltung.spieler.model.domain.SpielerDTO;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.format.annotation.DateTimeFormat;

import dhsn.verwaltung.spieler.model.domain.Position;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    private int rueckennummer;
    

    public SpielerRegisterDTO(){}   

    public Integer getAlter() {
        return Period.between(geburtsdatum, LocalDate.now()).getYears();
    }
}

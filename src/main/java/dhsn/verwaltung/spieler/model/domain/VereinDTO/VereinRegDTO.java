package dhsn.verwaltung.spieler.model.domain.VereinDTO;

import dhsn.verwaltung.spieler.model.identity.Benutzer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VereinRegDTO {

    @Valid
    
    @NotBlank(message = "Name für den Verein erforderlich")
    private String name;

    @NotNull(message = "Ein Admin muss ausgewählt werden")
    private Benutzer admin;
    
    public VereinRegDTO(){};

}

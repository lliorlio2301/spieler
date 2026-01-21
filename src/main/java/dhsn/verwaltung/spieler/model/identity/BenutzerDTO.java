package dhsn.verwaltung.spieler.model.identity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BenutzerDTO {
    
    //Werkzeuge zur Festlegung, was in den Felder eingetragen werden können 
    @Valid

    @NotBlank(message = "Username muss eingetragen werden")
    private String username;
    
    @NotBlank(message = "Password muss eingetragen werden")
    @Size(min = 5, max = 100, message = "Password muss zwischen 5 & 15 lang sein")
    private String passwort;

    @NotNull(message = "Eine Rolle muss eingetragen werden")
    @Enumerated(EnumType.STRING)
    private Role role;

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    
}

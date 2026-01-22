package dhsn.verwaltung.spieler.model.identity.DTO;

import dhsn.verwaltung.spieler.model.identity.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BenutzerIdDTO {

     //Werkzeuge zur Festlegung, was in den Felder eingetragen werden können 
    @Valid

    private Long id;

    @NotBlank(message = "Username muss eingetragen werden")
    private String username;
    
    @NotBlank(message = "Password muss eingetragen werden")
    @Size(min = 5, max = 100, message = "Password muss zwischen 5 & 15 lang sein")
    private String passwort;

    @NotNull(message = "Eine Rolle muss eingetragen werden")
    @Enumerated(EnumType.STRING)
    private Role role;

    public BenutzerIdDTO(){}

    public BenutzerIdDTO(Long id, String username,String passwort,Role role) {
        this.id = id;
        this.username = username;
        this.passwort = passwort;
        this.role = role;
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}

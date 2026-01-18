package dhsn.verwaltung.spieler.model.identity;

import java.util.Collection;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//Diese Klasse ermöglicht eine saubere Übertragung von GUI zu Objekt
public class BenutzerDTO implements UserDetails {
    
    private String username;
    private String password;

    public BenutzerDTO(){}

    //Notwendig, um bei Service den Objekt zurückzusenden
    public BenutzerDTO(Benutzer benutzer) {
        this.username = benutzer.getUsername();
        this.password = benutzer.getPasswort();
    }

    //Override Methoden notwendig. SprSec verifiziert diese zwanghaft bei jedem User
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return java.util.Collections.emptyList();
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }
}

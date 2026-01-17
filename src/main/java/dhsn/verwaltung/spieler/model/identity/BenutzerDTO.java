package dhsn.verwaltung.spieler.model.identity;

import java.util.Collection;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//Diese Klasse ermöglicht eine saubere Übertragung von GUI zu Objekt
public class BenutzerDTO implements UserDetails {
    
    private String username;
    private String passwort;

    public BenutzerDTO(){}

    //Notwendig, um bei Service den Objekt zurückzusenden
    public BenutzerDTO(Benutzer benutzer) {
        benutzer.setPasswort(passwort);
        benutzer.setUsername(username);
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAuthorities'");
    }

    @Override
    public @Nullable String getPassword() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPassword'");
    }
}

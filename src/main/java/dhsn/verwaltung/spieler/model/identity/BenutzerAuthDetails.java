package dhsn.verwaltung.spieler.model.identity;

import java.util.Collection;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//Für die Auth Prozess
public class BenutzerAuthDetails implements UserDetails {
    
    private Long id;
    private String username;
    private String password;
    private Role role;

    public BenutzerAuthDetails(){}

    //Notwendig, um bei Service den Objekt zurückzusenden
    public BenutzerAuthDetails(Benutzer benutzer) {
        this.id = benutzer.getId();
        this.username = benutzer.getUsername();
        this.password = benutzer.getPasswort();
        this.role = benutzer.getRole();
    }
    
    public Long getId() {
        return id;
    }

    //Override Methoden notwendig. SprSec verifiziert diese zwanghaft bei jedem User
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }
}

package dhsn.verwaltung.spieler.model.identity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "benutzer")
//Spieler hat eine eigene Tabelle, aber taucht hier auch auf 
//Es werden 2 INSERT automatisch ausgeführt
@Inheritance(strategy = InheritanceType.JOINED)
public class Benutzer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;
    
    private String passwort;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Benutzer(){}

    public Benutzer(String username, String passwort, Role role) {
        this.username = username;
        this.passwort = passwort;
        this.role = role;
    }

    public Long getId(){
        return this.id;
    }
    public String getUsername() {
        return username;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setUsername(String username) {
        this.username = username;
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

package dhsn.verwaltung.spieler.model.domain;

import java.util.ArrayList;
import java.util.List;

import dhsn.verwaltung.spieler.model.identity.Benutzer;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter //Erstellt automatisch Getters und Setters 
@Entity
@Table(name = "verein")
public class Verein {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "verein", fetch = FetchType.LAZY)
    private List<Spieler> spielerListe = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "id", unique = true, nullable = true)
    private Benutzer admin;

    private String name;
    
    public Verein(){}

    public Verein(Benutzer admin, String name) {
        this.admin = admin;
        this.name = name;
    }
}

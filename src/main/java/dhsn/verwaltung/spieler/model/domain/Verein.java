package dhsn.verwaltung.spieler.model.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Verein {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(
        mappedBy = "verein",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Spieler> spieler = new ArrayList<>(); //Initializiert hier anstatt an Konstruktor wegen NullPointerException Risiko

    private String name;

    public Verein(){} //Erforderlicher Konstruktor, damit JPA arbeiten kann


    public Verein(String name) {
        this.name = name;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Spieler> getSpieler() {
        return spieler;
    }

    public void setSpieler(List<Spieler> spieler) {
        this.spieler = spieler;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
}

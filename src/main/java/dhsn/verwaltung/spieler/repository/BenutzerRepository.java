package dhsn.verwaltung.spieler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dhsn.verwaltung.spieler.model.identity.Benutzer;

@Repository
public interface BenutzerRepository extends JpaRepository<Benutzer, Long> {
    Benutzer findByUsername(String username);
}

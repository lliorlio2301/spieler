package dhsn.verwaltung.spieler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dhsn.verwaltung.spieler.model.identity.Benutzer;

@Repository
public interface BenutzerRepository extends JpaRepository<Benutzer, Long> {
    Benutzer findByUsername(String username);

    @Query("SELECT b FROM Benutzer b WHERE b.verwalteterVerein IS NULL AND b.role = 'ROLE_ADMIN'")
    List<Benutzer> findFreieAdmins();
}


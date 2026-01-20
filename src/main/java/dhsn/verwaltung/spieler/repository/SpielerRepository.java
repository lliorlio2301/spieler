package dhsn.verwaltung.spieler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dhsn.verwaltung.spieler.model.domain.Spieler;

@Repository
public interface SpielerRepository extends JpaRepository<Spieler, Long> {

}

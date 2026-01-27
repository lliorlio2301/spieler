package dhsn.verwaltung.spieler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dhsn.verwaltung.spieler.model.domain.Spieler;
import jakarta.transaction.Transactional;

@Repository
public interface SpielerRepository extends JpaRepository<Spieler, Long> {

    public List<Spieler> findByVereinIsNull();
    @Modifying
    @Transactional
    @Query(value = " INSERT INTO spieler (benutzer_id, vorname, nachname, position, rueckennummer, geburtsdatum, will_gehalts_erhoeung) "
        + " VALUES (:id, 'Neu', 'Neu', 0, :rueckennummer, '2000-10-01', 'f')", nativeQuery = true) //native Umschaltung zu SQLQuery
    public void adminZuSpieler(@Param("id") Long id, int rueckennummer);

    @Modifying
    @Transactional
    //Query sonst deleteById, löscht auch bei Benutzer Tabelle
    @Query(value = " DELETE FROM spieler WHERE benutzer_id = :id ", nativeQuery = true) //native Umschaltung zu SQLQuery
    public void deleteNurBeiSpieler(@Param("id") Long id);

    @Query(value = " SELECT rueckennummer FROM spieler", nativeQuery = true) //native Umschaltung zu SQLQuery
    public List<Integer> getRueckNummer();
}

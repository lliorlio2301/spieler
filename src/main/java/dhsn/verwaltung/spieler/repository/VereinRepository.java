package dhsn.verwaltung.spieler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dhsn.verwaltung.spieler.model.domain.Verein;

@Repository
public interface VereinRepository extends JpaRepository<Verein, Long> {
    Verein findByAdminId(Long id);    
    
}

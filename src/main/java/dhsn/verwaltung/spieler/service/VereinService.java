package dhsn.verwaltung.spieler.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dhsn.verwaltung.spieler.model.domain.Spieler;
import dhsn.verwaltung.spieler.model.domain.Verein;
import dhsn.verwaltung.spieler.model.identity.Benutzer;
import dhsn.verwaltung.spieler.repository.BenutzerRepository;
import dhsn.verwaltung.spieler.repository.VereinRepository;

@Service
public class VereinService {
    
    private VereinRepository vereinRepository;
    private BenutzerRepository benutzerRepository;

    public VereinService(VereinRepository vRepo, BenutzerRepository bRepo) {
        this.vereinRepository = vRepo;
        this.benutzerRepository = bRepo;
    }

    public Verein getVerein(Long id) {
        Verein verein = vereinRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID: "+id+" unbekannt" ));
        return verein; 
    }

    public void save(Verein vereinDB) {
        vereinRepository.save(vereinDB);
    }

    public List<Verein> getAlleVereine() {
        return vereinRepository.findAll();
    }

    public void deleteVerein(Long id) {
        Verein verein = vereinRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID: "+id+" unbekannt" ));

        for (Spieler spieler : verein.getSpielerListe()) {
            spieler.setVerein(null);
            spieler.setRueckennummer(0);
        }

        if (verein.getAdmin()!=null) {
            Benutzer admin = benutzerRepository.findById(verein.getAdmin().getId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID: "+id+" unbekannt" ));;
            admin.setVerwalteterVerein(null);
        }
        
        System.out.println("VEREIN GELÖSCHT: " +verein.getName());
        vereinRepository.delete(verein);
    }

    public boolean existiertVereinByAdminId(Long id) {
        Verein verein = vereinRepository.findByAdminId(id);
        
        if (verein == null) return false;         
        return true;
    }

    public void entferneAdminAusVerein(Long admin_id) {
        Verein verein = vereinRepository.findByAdminId(admin_id);
        verein.setAdmin(null);
    }
}

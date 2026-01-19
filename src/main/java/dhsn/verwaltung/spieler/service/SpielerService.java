package dhsn.verwaltung.spieler.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dhsn.verwaltung.spieler.model.domain.Spieler;
import dhsn.verwaltung.spieler.repository.SpielerRepository;

@Service
public class SpielerService {
    private SpielerRepository sr;

    public SpielerService (SpielerRepository sr) {
        this.sr = sr;
    }

    public List<Spieler> getAlleSpieler() {
        return sr.findAll();
    }

}

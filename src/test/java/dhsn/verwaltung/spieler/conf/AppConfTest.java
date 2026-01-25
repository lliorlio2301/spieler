package dhsn.verwaltung.spieler.conf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dhsn.verwaltung.spieler.model.domain.Spieler;
import dhsn.verwaltung.spieler.model.identity.Role;
import dhsn.verwaltung.spieler.repository.BenutzerRepository;
import dhsn.verwaltung.spieler.repository.SpielerRepository;

@SpringBootTest
public class AppConfTest {

    @Autowired
    BenutzerRepository benutzerRepository;

    @Autowired
    SpielerRepository spielerRepository;

    @Test
    void werdenAlleDatenInDerDbGeladen() {
        long anzahlAllerBenutzer = benutzerRepository.count();
        assertEquals(9, anzahlAllerBenutzer);
        long anzahlAllerSpieler = benutzerRepository.findAll().stream().filter(s->s.getRole().equals(Role.ROLE_SPIELER)).collect(Collectors.counting());
        assertEquals(5, anzahlAllerSpieler);
        long einzigeSuperUser = benutzerRepository.findAll().stream().filter(s->s.getRole().equals(Role.ROLE_SUPER)).collect(Collectors.counting());
        assertEquals(1, einzigeSuperUser);
    }

    @Test 
    void istDieRuecknummerEindeutig() {
        Long benutzteNummer = spielerRepository.findAll().stream().map(Spieler::getRueckennummer).distinct().count();
        assertEquals(5, benutzteNummer);
    }
}

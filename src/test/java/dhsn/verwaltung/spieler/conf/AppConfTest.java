package dhsn.verwaltung.spieler.conf;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        
        assertEquals(benutzerRepository.findAll().size(), anzahlAllerBenutzer);
        
        long anzahlAllerSpieler = benutzerRepository.findAll().stream().filter(s->s.getRole().equals(Role.ROLE_SPIELER)).collect(Collectors.counting());
        
        assertEquals(benutzerRepository.findAll().stream().
            filter(s->s.getRole().equals(Role.ROLE_SPIELER)).
            collect(Collectors.toList()).size(), 
            anzahlAllerSpieler);

        long einzigeSuperUser = benutzerRepository.findAll().stream().filter(s->s.getRole().equals(Role.ROLE_SUPER)).collect(Collectors.counting());
        assertEquals(1, einzigeSuperUser);
    }
}

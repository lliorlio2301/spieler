package dhsn.verwaltung.spieler.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dhsn.verwaltung.spieler.model.domain.Spieler;
import dhsn.verwaltung.spieler.model.identity.Benutzer;
import dhsn.verwaltung.spieler.model.identity.Role;


@SpringBootTest
public class SpielerRepositoryTest {

    @Autowired
    SpielerRepository spielerRepository;

    @Autowired
    BenutzerRepository benutzerRepository; 

    @Test
    public void testBenutzerAdmin(){
        Benutzer benutzer = new Benutzer("testUser", "test12", Role.ROLE_ADMIN);
        benutzerRepository.save(benutzer);
        Long id = benutzerRepository.findByUsername("testUser").getId();
        spielerRepository.adminZuSpieler(id, 0);
        Spieler neuerSpieler = spielerRepository.findById(id).orElse(null);
        assertEquals(id, neuerSpieler.getId());
    }

    @Test
    public void nurDeleteBeiSpieler(){
        Spieler spieler = new Spieler("testUser", "test12", Role.ROLE_SPIELER);
        spielerRepository.save(spieler);

        Long id = benutzerRepository.findByUsername("testUser").getId();

        spielerRepository.deleteNurBeiSpieler(id);

        Spieler spielerNull = spielerRepository.findById(id).orElse(null);
        
        assertNull(spielerNull);

        Benutzer benutzer = benutzerRepository.findById(id).orElseThrow();
        assertNotNull(benutzer);
        assertEquals(id, benutzer.getId()); 
    }
}

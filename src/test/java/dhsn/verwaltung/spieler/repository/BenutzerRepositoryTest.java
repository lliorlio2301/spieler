package dhsn.verwaltung.spieler.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import dhsn.verwaltung.spieler.model.identity.Benutzer;

//JPA TEST, um die H2-DB im Memory zu benutzen. Leichter als Postgre
@DataJpaTest
public class BenutzerRepositoryTest {
    
    @Autowired
    BenutzerRepository benutzerRepository;
    
    @Test
    void usernameData() {
        Benutzer benutzer = new Benutzer();
        benutzer.setUsername("username");
        benutzerRepository.save(benutzer);
        Benutzer benutzerTest = benutzerRepository.findByUsername("username");
        assertEquals(benutzerTest, benutzer);
    }
}

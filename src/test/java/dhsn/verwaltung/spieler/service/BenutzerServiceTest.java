package dhsn.verwaltung.spieler.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import dhsn.verwaltung.spieler.model.identity.Benutzer;
import dhsn.verwaltung.spieler.model.identity.Role;
import dhsn.verwaltung.spieler.repository.BenutzerRepository;
import dhsn.verwaltung.spieler.repository.SpielerRepository;
import dhsn.verwaltung.spieler.repository.VereinRepository;

@SpringBootTest
public class BenutzerServiceTest {
    @Autowired
    private BenutzerService benutzerService;
    @Autowired
    private BenutzerRepository benutzerRepository;
    @Autowired
    private SpielerRepository spielerRepository;
    @Autowired
    private VereinRepository vereinRepository;


    @Test
    public void testenFreieAdmins(){

        //Die DB muss bereignigt werden von den vorherigen Tests, und zwar alles weil FK vorhanden sind
        //InBatch schickt direkt den SQLBefehl an die DB. Normales DeleteAll lädt alles und deswegen treten dann Fehler auf
        spielerRepository.deleteAllInBatch();
        vereinRepository.deleteAllInBatch();
        benutzerRepository.deleteAllInBatch();

        List<Benutzer> admins = List.of(
            new Benutzer("testenUser1", "test1", Role.ROLE_ADMIN),
            new Benutzer("testenUser2", "test1", Role.ROLE_ADMIN),
            new Benutzer("testenUser3", "test1", Role.ROLE_ADMIN)
        );

        benutzerRepository.saveAll(admins);

        List<Benutzer> listeAdminsOhneTeam = benutzerService.getFreieAdmins();

        assertFalse(listeAdminsOhneTeam.isEmpty());

        assertEquals(3, listeAdminsOhneTeam.size());

        Boolean alleFreie = listeAdminsOhneTeam.stream().allMatch(a->a.getVerwalteterVerein()==null);
        assertTrue(alleFreie);
    }
}

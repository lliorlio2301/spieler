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

@SpringBootTest
public class BenutzerServiceTest {
    @Autowired
    private BenutzerService benutzerService;
    @Autowired
    private BenutzerRepository benutzerRepository;


    @Test
    public void testenFreieAdmins(){

        List<Benutzer> admins = List.of(
            new Benutzer("test1", "test1", Role.ROLE_ADMIN),
            new Benutzer("test2", "test1", Role.ROLE_ADMIN),
            new Benutzer("test3", "test1", Role.ROLE_ADMIN)
        );

        benutzerRepository.saveAll(admins);

        List<Benutzer> listeAdminsOhneTeam = benutzerService.getFreieAdmins();

        assertFalse(listeAdminsOhneTeam.isEmpty());

        assertEquals(3, listeAdminsOhneTeam.size());

        Boolean alleFreie = listeAdminsOhneTeam.stream().allMatch(a->a.getVerwalteterVerein()==null);
        assertTrue(alleFreie);
    }
}

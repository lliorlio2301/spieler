package dhsn.verwaltung.spieler.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import dhsn.verwaltung.spieler.model.domain.Spieler;

@DataJpaTest
public class SpielerRepositoryTest {

    @Autowired
    SpielerRepository spielerRepository;
}

package dhsn.verwaltung.spieler.service;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dhsn.verwaltung.spieler.model.domain.Spieler;
import dhsn.verwaltung.spieler.repository.SpielerRepository;
import jakarta.transaction.Transactional;

@Service
public class SpielerService {
  private SpielerRepository sr;
  private PasswordEncoder passwordEncoder;

  public SpielerService(SpielerRepository sr, PasswordEncoder passwordEncoder) {
    this.sr = sr;
    this.passwordEncoder = passwordEncoder;
  }

  public Spieler getSpieler(Long id) {
    return sr.findById(id).orElseThrow(()->new UsernameNotFoundException("ID ist unbekannt: " +id));
  }

  public List<Spieler> getAlleSpieler() {
    return sr.findAll();
  }
  
  

  @Transactional
  public Spieler speichernEditSpieler(Spieler formularSpieler, Long id) 
  throws UsernameNotFoundException {
    Spieler datenBankSpieler = sr.findById(id).orElseThrow(() -> new UsernameNotFoundException("Id nicht bekannt"));

    datenBankSpieler.setUsername(formularSpieler.getUsername());
    datenBankSpieler.setVorname(formularSpieler.getVorname());
    datenBankSpieler.setNachname(formularSpieler.getNachname());
    datenBankSpieler.setGeburtsdatum(formularSpieler.getGeburtsdatum());
    datenBankSpieler.setPosition(formularSpieler.getPosition());
    datenBankSpieler.setRueckennummer(formularSpieler.getRueckennummer());

    if (
      !formularSpieler.getPasswort().isEmpty() &&
      formularSpieler.getPasswort() != null 
      && !datenBankSpieler.getPasswort().equals(passwordEncoder.encode(formularSpieler.getPasswort()))
    ) {
        
      datenBankSpieler.setPasswort(passwordEncoder.encode(formularSpieler.getPasswort()));
    }
    return sr.save(datenBankSpieler);
  }

}

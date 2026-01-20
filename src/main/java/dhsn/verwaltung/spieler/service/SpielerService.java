package dhsn.verwaltung.spieler.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dhsn.verwaltung.spieler.model.domain.Spieler;
import dhsn.verwaltung.spieler.model.domain.SpielerBasicDTO;
import dhsn.verwaltung.spieler.model.domain.SpielerRegisterDTO;
import dhsn.verwaltung.spieler.model.identity.Benutzer;
import dhsn.verwaltung.spieler.repository.SpielerRepository;
import jakarta.transaction.Transactional;

@Service
public class SpielerService {
  private SpielerRepository spielerRepo;
  private PasswordEncoder passwordEncoder;

  public SpielerService(
    SpielerRepository sr, PasswordEncoder passwordEncoder
  ) {
    this.spielerRepo = sr;
    this.passwordEncoder = passwordEncoder;
  }

  public SpielerBasicDTO getBasicSpielerDTO(Long id) {
    Spieler spieler = spielerRepo.findById(id).orElseThrow(()->new UsernameNotFoundException("ID ist unbekannt: " +id));
    SpielerBasicDTO sbdto = new SpielerBasicDTO();
    sbdto.setId(spieler.getId());
    sbdto.setVorname(spieler.getVorname());
    sbdto.setGeburtsdatum(spieler.getGeburtsdatum());
    sbdto.setNachname(spieler.getNachname());
    sbdto.setPosition(spieler.getPosition());
    sbdto.setRueckennummer(spieler.getRueckennummer());
    return sbdto;
  }

  public List<SpielerBasicDTO> getAlleSpielerBasic() {
    return spielerRepo.findAll().stream().
    map(SpielerBasicDTO::new)
    .collect(Collectors.toList());
  }

  public List<Spieler> getAlleSpielerAdmin() {
    return spielerRepo.findAll();
  }

  public Benutzer register(SpielerRegisterDTO spielerDTO) {
    Spieler neuerSpieler = new Spieler(spielerDTO.getUsername(),
    passwordEncoder.encode(spielerDTO.getPasswort()), spielerDTO.getRole(),
    spielerDTO.getVorname(), spielerDTO.getNachname(), spielerDTO.getGeburtsdatum(),
    spielerDTO.getPosition(), spielerDTO.getRueckennummer());
    return neuerSpieler;
  }
  

//Gewährleistung der Datenintegrität. Alles wird ausgeführt oder nichts
  @Transactional
  public void speichernEditSpieler(SpielerBasicDTO spielerBDTO, Long id) {
    
    Spieler datenBankSpieler = spielerRepo.findById(id).orElseThrow(() -> 
    new ResponseStatusException(HttpStatus.NOT_FOUND, "ID: "+id+" unbekannt"));

    datenBankSpieler.setVorname(spielerBDTO.getVorname());
    datenBankSpieler.setNachname(spielerBDTO.getNachname());
    datenBankSpieler.setGeburtsdatum(spielerBDTO.getGeburtsdatum());
    datenBankSpieler.setPosition(spielerBDTO.getPosition());
    datenBankSpieler.setRueckennummer(spielerBDTO.getRueckennummer());

    if (
      !spielerBDTO.getPasswort().isEmpty() &&
      spielerBDTO.getPasswort() != null 
      && !datenBankSpieler.getPasswort().equals(passwordEncoder.encode(spielerBDTO.getPasswort()))
    ) {
        
      datenBankSpieler.setPasswort(passwordEncoder.encode(spielerBDTO.getPasswort()));
    }
    spielerRepo.save(datenBankSpieler);
  }

  @Transactional
  public void deleteSpieler(Long id) {
    //Test ob ID in DB vorhanden ist
    spielerRepo.findById(id).orElseThrow(() -> 
    new ResponseStatusException(HttpStatus.NOT_FOUND, "ID: "+id+" unbekannt"));
    spielerRepo.deleteById(id);
  }
}

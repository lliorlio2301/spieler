package dhsn.verwaltung.spieler.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dhsn.verwaltung.spieler.model.domain.Spieler;
import dhsn.verwaltung.spieler.model.domain.DTO.SpielerRegisterDTO;
import dhsn.verwaltung.spieler.model.domain.DTO.SpielerUpdateDTO;
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

  public SpielerUpdateDTO getBasicSpielerDTO(Long id) {
    Spieler spieler = spielerRepo.findById(id).orElseThrow(()->new UsernameNotFoundException("ID ist unbekannt: " +id));
    SpielerUpdateDTO spielerDTO = new SpielerUpdateDTO();
    spielerDTO.setId(spieler.getId());
    spielerDTO.setVorname(spieler.getVorname());
    spielerDTO.setGeburtsdatum(spieler.getGeburtsdatum());
    spielerDTO.setNachname(spieler.getNachname());
    spielerDTO.setPosition(spieler.getPosition());
    spielerDTO.setRueckennummer(spieler.getRueckennummer());
    spielerDTO.setWillGehaltsErhoeung(spieler.isWillGehaltsErhoeung());
    return spielerDTO;
  }

  public List<SpielerUpdateDTO> getAlleSpielerBasic() {
    return spielerRepo.findAll().stream().
    map(SpielerUpdateDTO::new)
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
    spielerRepo.save(neuerSpieler);
    return neuerSpieler;
  }
  

//Gewährleistung der Datenintegrität. Alles wird ausgeführt oder nichts
  @Transactional
  public void speichernEditSpieler(SpielerUpdateDTO spielerBDTO, Long id) {
    
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

  public void setGehaltErhoung(Long id, boolean willErhoung) {
    Spieler spieler = spielerRepo.findById(id).orElseThrow(()->
      new ResponseStatusException(HttpStatus.NOT_FOUND, "ID: "+id+" unbekannt")
    );
    spieler.setWillGehaltsErhoeung(willErhoung);
    spielerRepo.save(spieler);
  }
}

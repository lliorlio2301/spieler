package dhsn.verwaltung.spieler.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dhsn.verwaltung.spieler.model.identity.Benutzer;
import dhsn.verwaltung.spieler.model.identity.BenutzerDTO;
import dhsn.verwaltung.spieler.repository.BenutzerRepository;
import jakarta.transaction.Transactional;

@Service
public class BenutzerService implements UserDetailsService {

  private BenutzerRepository benutzerRepository;
  private PasswordEncoder passwordEncoder;

  //Verantwortlich das eingegebene Passwort zu encrypten
  private BCryptPasswordEncoder bEncoder = new BCryptPasswordEncoder(4);

  public BenutzerService (BenutzerRepository benutzerRepository, PasswordEncoder pe) {
    this.benutzerRepository = benutzerRepository;
    this.passwordEncoder = pe;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    System.out.println("Anmeldungversuch. Username: " + username);
    
    Benutzer benutzer = benutzerRepository.findByUsername(username);

    if (benutzer==null) {
      throw new UsernameNotFoundException("Username: "+ username+" wurde nicht gefunden");
    }
    return new BenutzerDTO(benutzer);
  }

  public Benutzer register(Benutzer benutzer) {
    benutzer.setPasswort(bEncoder.encode(benutzer.getPasswort()));
    // Gibt Objekt zurück, da erst bei save() die Id in der DB erstellt wird
    // Jetzt als Prüfer für Methode im Controller
    return benutzerRepository.save(benutzer);  
  }

  public List<Benutzer> getAllBenutzer() {
    return benutzerRepository.findAll();
  }

  public Benutzer getBenutzerById(Long id) {
    return benutzerRepository.findById(id).orElseThrow(()-> 
      new ResponseStatusException(HttpStatus.NOT_FOUND, "ID: "+id+" unbekannt"));
  }

  @Transactional
  public void speicherEditBenutzer(Benutzer formularBenutzer, Long id) {

    Benutzer datenBankBenutzer = benutzerRepository.findById(id).
    orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID: "+id+" unbekannt"));

    datenBankBenutzer.setUsername(formularBenutzer.getUsername());
    datenBankBenutzer.setRole(formularBenutzer.getRole());

    if (
      !passwordEncoder.encode(formularBenutzer.getPasswort()).equals(datenBankBenutzer.getPasswort()) &&
      !formularBenutzer.getPasswort().isEmpty() && formularBenutzer.getPasswort() != null

    ) {
      datenBankBenutzer.setPasswort(passwordEncoder.encode(formularBenutzer.getPasswort()));
    }

    benutzerRepository.save(datenBankBenutzer);
  }

  @Transactional
  public void deleteBenutzer(Long id) {
    if (!benutzerRepository.existsById(id)) {
     throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID: "+id+" unbekannt"); 
    }
    benutzerRepository.deleteById(id);
  }
}

package dhsn.verwaltung.spieler.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dhsn.verwaltung.spieler.model.identity.Benutzer;
import dhsn.verwaltung.spieler.model.identity.BenutzerDTO;
import dhsn.verwaltung.spieler.repository.BenutzerRepository;

@Service
public class BenutzerService implements UserDetailsService {

  private BenutzerRepository benutzerRepository;

  //Verantwortlich das eingegebene Passwort zu encrypten
  private BCryptPasswordEncoder bEncoder = new BCryptPasswordEncoder(4);

  public BenutzerService (BenutzerRepository benutzerRepository) {
    this.benutzerRepository = benutzerRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    System.out.println("Anmeldungversuch. Username: " + username);
    
    Benutzer benutzer = benutzerRepository.findByUsername(username);

    if (benutzer==null) {
      throw new UsernameNotFoundException("User wurde nicht gefunden");
    }
    return new BenutzerDTO(benutzer);
  }

  public Benutzer register(Benutzer benutzer) {
    benutzer.setPasswort(bEncoder.encode(benutzer.getPasswort()));
    return benutzerRepository.save(benutzer);
  }
}

package dhsn.verwaltung.spieler.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dhsn.verwaltung.spieler.model.identity.Benutzer;
import dhsn.verwaltung.spieler.model.identity.BenutzerAuthDetails;
import dhsn.verwaltung.spieler.repository.BenutzerRepository;

@Service
public class BenutzerAuthDetailsService implements UserDetailsService {

    private BenutzerRepository benutzerRepository;

    public BenutzerAuthDetailsService(BenutzerRepository br) {
        this.benutzerRepository = br;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    System.out.println("Anmeldungversuch. Username: " + username);
    
    Benutzer benutzer = benutzerRepository.findByUsername(username);

    if (benutzer==null) {
      throw new UsernameNotFoundException("Username: "+ username+" wurde nicht gefunden");
    }
    return new BenutzerAuthDetails(benutzer);
  }
    
}

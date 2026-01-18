package dhsn.verwaltung.spieler.conf;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import dhsn.verwaltung.spieler.model.identity.Benutzer;
import dhsn.verwaltung.spieler.model.identity.Role;
import dhsn.verwaltung.spieler.repository.BenutzerRepository;

//Um Methoden als erstes auszuführen

@Configuration
public class AppConf {
   
    //Kann auch bei App Klasse als Implementierung des Interface, ist es aber weniger flexibel 
    @Bean
    public CommandLineRunner superUserAnlegen(
        BenutzerRepository benutzerRepository,
        PasswordEncoder passwordEncoder
     ) {
        // Lambda mit {....} weil es mehrere Methoden innerhalb dieser einzigen Methode gibt   
        return commandLineRunner -> {
            Benutzer benutzer = new Benutzer();
            benutzer.setUsername("SuperAdmin");
            benutzer.setPasswort(passwordEncoder.encode("welcome"));
            benutzer.setRole(Role.ROLE_SUPER);
            benutzerRepository.save(benutzer);
            System.out.printf("Superadmin %n%s %n%s", "Username: SuperAdmin", "welcome");
     };
    }
    
}

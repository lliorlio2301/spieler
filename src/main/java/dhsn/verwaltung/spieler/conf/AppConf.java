package dhsn.verwaltung.spieler.conf;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import dhsn.verwaltung.spieler.model.domain.Spieler;
import dhsn.verwaltung.spieler.model.domain.Position;
import dhsn.verwaltung.spieler.model.identity.Benutzer;
import dhsn.verwaltung.spieler.model.identity.Role;
import dhsn.verwaltung.spieler.repository.BenutzerRepository;
import dhsn.verwaltung.spieler.repository.SpielerRepository;

//Um Methoden als erstes auszuführen

@Configuration
public class AppConf {
   
    //Kann auch bei App Klasse als Implementierung des Interface, ist es aber weniger flexibel 
    @Bean
    public CommandLineRunner superUserAnlegen(
        BenutzerRepository benutzerRepository,
        SpielerRepository spielerRepository,
        PasswordEncoder passwordEncoder
     ) {
        // Lambda mit {....} weil es mehrere Methoden innerhalb dieser einzigen Methode gibt   
        return commandLineRunner -> {
            Benutzer benutzer = new Benutzer();
            benutzer.setUsername("SuperAdmin");
            benutzer.setPasswort(passwordEncoder.encode("welcome"));
            benutzer.setRole(Role.ROLE_SUPER);
            benutzerRepository.save(benutzer);
            System.out.printf("Superadmin %n%s %n%s", "Username: SuperAdmin", "Passwort: welcome");

            //Objekte Generieren für Testen
            List<Spieler> testSpieler = List.of(
                new Spieler("jorge.osorio", passwordEncoder.encode("welcome1"), Role.ROLE_SPIELER ,  
                            "Jorge", "Osorio", 
                            LocalDate.of(2004, 1, 23), 
                            Position.MITTELFELD, 4),
                new Spieler("titan.olli", passwordEncoder.encode("welcome2"), Role.ROLE_SPIELER ,
                            "Oliver", "Kahn", 
                            LocalDate.of(1995, 6, 15), 
                            Position.TORWART, 1),
                new Spieler("the.wall", passwordEncoder.encode("welcome3"), Role.ROLE_SPIELER ,
                            "Niklas", "Süle", 
                            LocalDate.of(1997, 9, 3), 
                            Position.ABWEHRSPIELER, 25),
                new Spieler("goalgetter", passwordEncoder.encode("welcome4"), Role.ROLE_SPIELER ,
                            "Jamal", "Musiala", 
                            LocalDate.of(2003, 2, 26), 
                            Position.MITTELSTUERMER, 10),
                new Spieler("kimmich.j", passwordEncoder.encode("welcome5"), Role.ROLE_SPIELER ,
                            "Joshua", "Kimmich", 
                            LocalDate.of(1995, 2, 8), 
                            Position.MITTELFELD, 6)
            );
        spielerRepository.saveAll(testSpieler);
     };
    }
    
}

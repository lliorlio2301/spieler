package dhsn.verwaltung.spieler.conf;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import dhsn.verwaltung.spieler.model.domain.Spieler;
import dhsn.verwaltung.spieler.model.domain.Verein;
import dhsn.verwaltung.spieler.model.domain.Position;
import dhsn.verwaltung.spieler.model.identity.Benutzer;
import dhsn.verwaltung.spieler.model.identity.Role;
import dhsn.verwaltung.spieler.repository.BenutzerRepository;
import dhsn.verwaltung.spieler.repository.SpielerRepository;
import dhsn.verwaltung.spieler.repository.VereinRepository;

//Um Methoden als erstes auszuführen

@Configuration
public class AppConf {
   
    //Kann auch bei App Klasse als Implementierung des Interface, ist es aber weniger flexibel 
    @Bean
    public CommandLineRunner superUserAnlegen(
        BenutzerRepository benutzerRepository,
        SpielerRepository spielerRepository,
        VereinRepository vereinRepository,
        PasswordEncoder passwordEncoder
     ) {
        // Lambda mit {....} weil es mehrere Methoden innerhalb dieser einzigen Methode gibt   
        return commandLineRunner -> {

            if (benutzerRepository.findByUsername("SuperAdmin")==null) {
                Benutzer SuperUser = new Benutzer();
                SuperUser.setUsername("SuperAdmin");
                SuperUser.setPasswort(passwordEncoder.encode("superadmin"));
                SuperUser.setRole(Role.ROLE_SUPER);
                benutzerRepository.save(SuperUser);
                System.out.printf("Superadmin %n%s %n%s", "Username: SuperAdmin", "Passwort: welcome");
            }

            if (vereinRepository.count()==0 && spielerRepository.count()==0) {
                
                List<Benutzer> testAdmin = List.of(
                new Benutzer("adminBayern", passwordEncoder.encode("bayernAdmin"), Role.ROLE_ADMIN),
                new Benutzer("adminDortmund", passwordEncoder.encode("dortmundAdmin"), Role.ROLE_ADMIN),
                new Benutzer("adminLeverkusen", passwordEncoder.encode("leverkusenAdmin"), Role.ROLE_ADMIN)
                );
                benutzerRepository.saveAll(testAdmin);

                    Verein bayern = new Verein(testAdmin.get(0),"Bayern München");
                    Verein dortmund = new Verein(testAdmin.get(1),"Borussia Dortmund");
                    Verein leverkusen = new Verein(testAdmin.get(2),"Bayer Leverkusen");
                    vereinRepository.saveAll(List.of(bayern, dortmund, leverkusen));

                //Objekte Generieren für Testen
                List<Spieler> testFreieSpieler = List.of(
                    new Spieler("jorge.osorio", passwordEncoder.encode("frei"), Role.ROLE_SPIELER,  
                                "Jorge", "Osorio", 
                                LocalDate.of(2004, 1, 23), 
                                Position.MITTELFELD),
                    new Spieler("juan.diego", passwordEncoder.encode("frei"), Role.ROLE_SPIELER,  
                                "Juan", "Miño", 
                                LocalDate.of(2004, 1, 23), 
                                Position.MITTELFELD),
                    new Spieler("mateo.salazar", passwordEncoder.encode("frei"), Role.ROLE_SPIELER,  
                                "Juan", "Miño", 
                                LocalDate.of(2004, 1, 23), 
                                Position.MITTELFELD),
                    new Spieler("josue.osorio", passwordEncoder.encode("frei"), Role.ROLE_SPIELER,  
                                "Josue", "Osorio", 
                                LocalDate.of(2004, 1, 23), 
                                Position.MITTELFELD),
                    new Spieler("titan.olli", passwordEncoder.encode("frei"), Role.ROLE_SPIELER,  
                            "Oliver", "Kahn", LocalDate.of(1969, 6, 15), Position.TORWART)
                );

                spielerRepository.saveAll(testFreieSpieler);

                List<Spieler> testBayernSpieler = List.of(
                    new Spieler("neuer.m", passwordEncoder.encode("1234"), Role.ROLE_SPIELER,  
                            "Manuel", "Neuer", LocalDate.of(1986, 3, 27), Position.TORWART, 1, bayern),
                    new Spieler("kane.h", passwordEncoder.encode("1234"), Role.ROLE_SPIELER,  
                                "Harry", "Kane", LocalDate.of(1993, 7, 28), Position.MITTELSTUERMER, 9, bayern),
                    new Spieler("kimmich.j", passwordEncoder.encode("1234"), Role.ROLE_SPIELER ,
                                "Joshua", "Kimmich", LocalDate.of(1995, 2, 8), Position.MITTELFELD, 6, bayern),
                    new Spieler("musiala.j", passwordEncoder.encode("1234"), Role.ROLE_SPIELER ,
                                "Jamal", "Musiala", LocalDate.of(2003, 2, 26), Position.MITTELSTUERMER, 42, bayern),
                    new Spieler("suele.n", passwordEncoder.encode("1234"), Role.ROLE_SPIELER ,
                            "Niklas", "Süle", LocalDate.of(1995, 9, 3), Position.ABWEHRSPIELER, 4, bayern)
                    
                );

                spielerRepository.saveAll(testBayernSpieler);

                List<Spieler> testDortmundSpieler = List.of(
                    new Spieler("brandt.j", passwordEncoder.encode("1234"), Role.ROLE_SPIELER,  
                                "Julian", "Brandt", LocalDate.of(1996, 5, 2), Position.MITTELFELD, 10, dortmund),
                    
                    new Spieler("kobel.g", passwordEncoder.encode("1234"), Role.ROLE_SPIELER,  
                                "Gregor", "Kobel", LocalDate.of(1997, 12, 6), Position.TORWART, 1, dortmund),
                    new Spieler("hummels.m", passwordEncoder.encode("1234"), Role.ROLE_SPIELER,  
                            "Mats", "Hummels", LocalDate.of(1988, 12, 16), Position.ABWEHRSPIELER, 15, dortmund),
                    new Spieler("schlotterbeck.n", passwordEncoder.encode("1234"), Role.ROLE_SPIELER,  
                                "Nico", "Schlotterbeck", LocalDate.of(1999, 12, 1), Position.ABWEHRSPIELER, 4, dortmund),
                    new Spieler("sabitzer.m", passwordEncoder.encode("1234"), Role.ROLE_SPIELER,  
                                "Marcel", "Sabitzer", LocalDate.of(1994, 3, 17), Position.MITTELFELD, 20, dortmund),
                    new Spieler("adeyemi.k", passwordEncoder.encode("1234"), Role.ROLE_SPIELER,  
                                "Karim", "Adeyemi", LocalDate.of(2002, 1, 18), Position.MITTELSTUERMER, 27, dortmund),
                    new Spieler("fullkrug.n", passwordEncoder.encode("1234"), Role.ROLE_SPIELER,  
                                "Niclas", "Füllkrug", LocalDate.of(1993, 2, 9), Position.MITTELSTUERMER, 14, dortmund)
                );

                spielerRepository.saveAll(testDortmundSpieler);

                List<Spieler> testLeverkusenSpieler = List.of(
                    new Spieler("wirtz.f", passwordEncoder.encode("1234"), Role.ROLE_SPIELER,  
                                "Florian", "Wirtz", LocalDate.of(2003, 5, 3), Position.MITTELFELD, 10, leverkusen),
                    new Spieler("xhaka.g", passwordEncoder.encode("1234"), Role.ROLE_SPIELER,  
                                "Granit", "Xhaka", LocalDate.of(1992, 9, 27), Position.MITTELFELD, 34, leverkusen),
                    new Spieler("hradecky.l", passwordEncoder.encode("1234"), Role.ROLE_SPIELER,  
                            "Lukas", "Hradecky", LocalDate.of(1989, 11, 24), Position.TORWART, 1, leverkusen),
                    new Spieler("tah.j", passwordEncoder.encode("1234"), Role.ROLE_SPIELER,  
                                "Jonathan", "Tah", LocalDate.of(1996, 2, 11), Position.ABWEHRSPIELER, 4, leverkusen),
                    new Spieler("frimpong.j", passwordEncoder.encode("1234"), Role.ROLE_SPIELER,  
                                "Jeremie", "Frimpong", LocalDate.of(2000, 12, 10), Position.ABWEHRSPIELER, 30, leverkusen),
                    new Spieler("grimaldo.a", passwordEncoder.encode("1234"), Role.ROLE_SPIELER,  
                                "Alejandro", "Grimaldo", LocalDate.of(1995, 9, 20), Position.ABWEHRSPIELER, 20, leverkusen),
                    new Spieler("boniface.v", passwordEncoder.encode("1234"), Role.ROLE_SPIELER,  
                                "Victor", "Boniface", LocalDate.of(2000, 12, 23), Position.MITTELSTUERMER, 22, leverkusen),
                    new Spieler("hofmann.j", passwordEncoder.encode("1234"), Role.ROLE_SPIELER,  
                            "Jonas", "Hofmann", LocalDate.of(1992, 7, 14), Position.MITTELFELD, 7, leverkusen)
                );

                spielerRepository.saveAll(testLeverkusenSpieler);
            }
     };
    }
    
}

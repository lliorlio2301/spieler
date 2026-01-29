package dhsn.verwaltung.spieler.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // so that Spring beginns with this Security Configuration
public class SecurityConf {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception { 
    // HTTP Security baut die Reihenfolge unsere angepasste SicherheitKette

    httpSecurity.authorizeHttpRequests(request -> request
      // css and images folder are allowed, otherwise the loginpage would see without desing
      .requestMatchers("/anmelden", "/spielerverwaltung/startseite" ,"/images/**", "/css/**").permitAll()
      //Fehlerursache sehen
      .requestMatchers("/error").permitAll()
      .requestMatchers("/spielerverwaltung/super/**").hasRole("SUPER")
      .requestMatchers("/spielerverwaltung/admin/**").hasAnyRole("ADMIN", "SUPER") //Für Seiten mit AdminRechten        
      .anyRequest().authenticated()); // It checks the auth any other request
    
    //Das übernimmt all den Auth.Prozess. Man braucht es nicht, im LoginController umzusetzen
    httpSecurity.formLogin(customizer -> customizer
      .loginPage("/anmelden")
      .permitAll()
      .loginProcessingUrl("/anmelden/login_auth") // PostRequest, which works the Auth
      .failureUrl("/anmelden?error") 
      .defaultSuccessUrl("/spielerverwaltung/homepage", true));

    httpSecurity.logout(customizer -> customizer
      .logoutUrl("/abmelden")
      .logoutSuccessUrl("/anmelden?bye") //
      .deleteCookies("JSESSIONID") //Sonst wird nicht richtig abgemeldet
      .permitAll()); // Every user can logout also when their token ist already expired
    
    return httpSecurity.build();
  }

  //Damit Bcrypt der Standard festgelegt wird
  @Bean
  public PasswordEncoder passwordEncoder() {
    //Encryptiert das eingetragenen Passwort und vergleicht den HASH mit dem vor DB
    return new BCryptPasswordEncoder(4);
  }

  //AuthProvider angepasst, um den AuthProzess selbst zu übernehmen. 
  //Wichtig für die Verbindung mit der DB
  @Bean
  public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);

    //Obwohl PasswordEnco schon oben deklariert wurde, muss es hier nochmal gemacht werden, 
    // weil der AuthProv neu und angepasst wird und dieses Feld muss nochmal konfiguriert werden
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }
}

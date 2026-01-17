package dhsn.verwaltung.spieler.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // so that Spring beginns with this Security Configuration
public class SecurityConf {

  private UserDetailsService userDetailsService;
  
  public SecurityConf(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception { 
    // HTTP Security baut die Reihenfolge unsere angepasste SicherheitKette

    httpSecurity.authorizeHttpRequests(request -> request

      // css and images folder are allowed, otherwise the loginpage would see without desing
      .requestMatchers("/login", "/spielerverwaltung/startseite", 
      "/registrierung" ,"/images/**", "/css/**").permitAll()
      .requestMatchers("/spielerverwaltung/admin/**").hasRole("ADMIN") //Für Seiten mit AdminRechten
      .anyRequest().authenticated()); // It checks the auth any other request
    
      //Das übernimmt all den Auth.Prozess. Man braucht es nicht, im LoginController umzusetzen
    httpSecurity.formLogin(customizer -> customizer
      .loginPage("/login")
      .loginProcessingUrl("/login/login_auth") // PostRequest, which works the Auth
      .defaultSuccessUrl("/spielerverwaltung/homepage", true));

    httpSecurity.logout(customizer -> customizer
      .logoutSuccessUrl("/login?bye") //
      .permitAll()); // Every user can logout also when their token ist already expired
    
    return httpSecurity.build();
  }

  //AuthProvider angepasst, um den AuthProzess selbst zu übernehmen. 
  // Wichtig für die Verbindung mit der DB
  @Bean
  public AuthenticationProvider authenticationProvider() {
    
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
    //Encryptiert das Passwort vom DB-Schicht
    authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder(4));
    return authenticationProvider;
  }
}

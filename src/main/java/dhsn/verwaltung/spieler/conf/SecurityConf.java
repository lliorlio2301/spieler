package dhsn.verwaltung.spieler.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // so that Spring beginns with this Security Configuration 
public class SecurityConf {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception { //HTTP Security 
        
        httpSecurity.authorizeHttpRequests(request -> request
            .requestMatchers("/login", "/static/**").permitAll() //static folder is allowed, otherwise the loginpage would see without desing
            .anyRequest().authenticated()); // It checks any other request .  
        
        httpSecurity.formLogin(customizer -> 
                customizer.loginPage("/login")
                .loginProcessingUrl("/login_auth") // PostRequest, which works the Auth
                .defaultSuccessUrl("/homepage", true));

        httpSecurity.logout(customizer -> customizer
            .logoutSuccessUrl("/login?bye") // 
            .permitAll()); // Every user can logout also when their token ist already expired  

        return httpSecurity.build();
    }
    
}

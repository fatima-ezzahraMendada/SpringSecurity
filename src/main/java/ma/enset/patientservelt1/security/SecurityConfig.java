package ma.enset.patientservelt1.security;

//import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
//2eme methode de securisation which is annotation enablemethodesecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;

    //Spring Security, hashing a password refers to the process
    // of transforming a plain-text password into a hashed or
    // encrypted form using a specific algorithm.
    // The purpose of hashing passwords is to enhance security
    // by protecting sensitive user information in case of unauthorized access to the stored password data.

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource){
      JdbcUserDetailsManager jdbcUserDetailsManager=new JdbcUserDetailsManager(dataSource);
      return jdbcUserDetailsManager;
    }
    //@Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(){
        InMemoryUserDetailsManager inMemoryUserDetailsManager=new InMemoryUserDetailsManager(
                //noop:no password encoder : cad je dis a spring security de ne pas utiliser un algorithme encoder pour encoder
                //le password
                //1234: mot de passe text claire
                User.withUsername("user1").password(passwordEncoder.encode("1234")).roles("user").build(),
                User.withUsername("user2").password(passwordEncoder.encode("1234")).roles("user").build(),
                User.withUsername("admin").password(passwordEncoder.encode("1234")).roles("user","admin").build()

    );
        return inMemoryUserDetailsManager;
    }
    //Remarque: securiser access par exemple a delete cote backend at first car il se peut quun user normal acceder a cette
    //fonctionalite par url:contratualisation
    @Bean
    //annotation bean means bli had methode securityfilterchain ghadi executa f demarrage oghadi
    //tcreer liya wahd objet securityFilterChain li ghadi nkhdmo bach npresonalise la configuration dyal spring security
    //springsecurity filter cest le premier qui va interpreter les requetes
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin();
        //1er methode de securisation
        /*httpSecurity.authorizeHttpRequests().requestMatchers("/admin/**").hasRole("admin");
        httpSecurity.authorizeHttpRequests().requestMatchers("/user/**").hasRole("user");
        */
        httpSecurity.authorizeHttpRequests().anyRequest().authenticated();
        httpSecurity.exceptionHandling().accessDeniedPage("/AccessDenied");
        return httpSecurity.build();

    }
}

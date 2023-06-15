package ma.enset.patientservelt1;

import ma.enset.patientservelt1.entities.Patient;
import ma.enset.patientservelt1.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;

@SpringBootApplication
public class PatientServelt1Application implements CommandLineRunner {
    @Autowired
    private PatientRepository patientRepository;

    public static void main(String[] args) {
        SpringApplication.run(PatientServelt1Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
      /*
        //1
        Patient p1=new Patient();
        p1.setId(null);
        p1.setName("fatima");
        p1.setAge(23);

        //2
        Patient p2=new Patient(null,"fatima",23);

        //3 avec annotation builder
        Patient p3=Patient.builder()
                .id(null)
                .name("fatima")
                .age(23)
                .build();

        */
       patientRepository.save(new Patient(null,"fatima",new Date(),false,1235));
       patientRepository.save(new Patient(null,"zahra",new Date(),false,1235));
       patientRepository.save(new Patient(null,"asmae",new Date(),false,1235));

    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    //@Bean
    CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager) {
        PasswordEncoder passwordEncoder = passwordEncoder();
        return args -> {
            jdbcUserDetailsManager.createUser(User.withUsername("user1").password(passwordEncoder.encode("1234")).roles("user").build()
                    );
            jdbcUserDetailsManager.createUser(User.withUsername("user2").password(passwordEncoder.encode("1234")).roles("user").build()
                    );
            jdbcUserDetailsManager.createUser(User.withUsername("admin").password(passwordEncoder.encode("1234")).roles("admin","user").build()
                    );
        };
    }
}

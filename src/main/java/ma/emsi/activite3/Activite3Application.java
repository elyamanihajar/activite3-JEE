package ma.emsi.activite3;

import ma.emsi.activite3.entities.Patient;
import ma.emsi.activite3.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class Activite3Application {
    @Autowired
    private PatientRepository patientRepository;

    public static void main(String[] args) {
        SpringApplication.run(Activite3Application.class, args);
    }

    /*@Override
    public void run(String... args) throws Exception {
        patientRepository.save(new Patient(null,"Mehdi",new Date(),false,23));
        patientRepository.save(new Patient(null,"Hajar",new Date(),true,20));
        patientRepository.save(new Patient(null,"Hind",new Date(),false,30));
        patientRepository.save(new Patient(null,"Bouchra",new Date(),true,40));

    }*/

    //@Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository) {
        return args -> {
            patientRepository.save(new Patient(null,"Mehdi",new Date(),false,23));
            patientRepository.save(new Patient(null,"Hajar",new Date(),true,20));
            patientRepository.save(new Patient(null,"Hind",new Date(),false,30));
            patientRepository.save(new Patient(null,"Bouchra",new Date(),true,40));
        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

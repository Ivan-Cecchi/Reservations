package ivancecchi.reservations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class BeansConfig {

    @Bean
    public Scanner getScanner() {
        return new Scanner(System.in);
    }
}

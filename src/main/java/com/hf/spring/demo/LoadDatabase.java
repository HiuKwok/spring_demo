package com.hf.spring.demo;

import com.hf.spring.demo.model.CarPark;
import com.hf.spring.demo.repository.CarParkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    /**
     * This block of code will be executed, once the application context is loaded.
     * @param repository
     * @return
     */
    @Bean
    CommandLineRunner initDatabase(CarParkRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new CarPark(
                    10L, "1", "2", "3", "4", false
            )));
        };
    }
}

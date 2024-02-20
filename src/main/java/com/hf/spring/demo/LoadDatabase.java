package com.hf.spring.demo;

import com.hf.spring.demo.model.CarPark;
import com.hf.spring.demo.model.Order;
import com.hf.spring.demo.model.Status;
import com.hf.spring.demo.repository.CarParkRepository;
import com.hf.spring.demo.repository.OrderRepository;
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
    CommandLineRunner initDatabase(CarParkRepository repository, OrderRepository orderRepository) {

        return args -> {
            log.info("Preloading " + repository.save(new CarPark(
                    10L, "1", "2", "3", "4", false
            )));

            orderRepository.save(new Order("MacBook Pro", Status.COMPLETED));
            orderRepository.save(new Order("iPhone", Status.IN_PROGRESS));
            orderRepository.findAll().forEach(order -> {
                log.info("Preloaded " + order);
            });
        };
    }
}

package com.hf.spring.demo.web;

import com.hf.spring.demo.model.CarPark;
import com.hf.spring.demo.repository.CarParkRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * RestController hints the Spring runtime to pick up for the REST endpoint.
 */
@RestController
public class CarParkController {

    private final CarParkRepository repository;

    public CarParkController(CarParkRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/carparks")
    List<CarPark> all() {
        return repository.findAll();
    }

    @PostMapping("/carparks")
    CarPark newCarPark(@RequestBody CarPark newCarPark) {
        return repository.save(newCarPark);
    }

    @GetMapping("/carparks/{id}")
    CarPark one(@PathVariable Long id) throws Exception {

        return repository.findById(id)
                .orElseThrow(() -> new Exception());
    }

    @PutMapping("/carparks/{id}")
    CarPark replaceCarPark(@RequestBody CarPark newCarPark,
                           @PathVariable Long id) {

        return repository.findById(id)
                .map(carPark -> {
                    carPark.setName(newCarPark.getName());
                    carPark.setDistrict(newCarPark.getDistrict());
                    carPark.setNature(newCarPark.getNature());
                    carPark.setEvFriendly(newCarPark.getEvFriendly());
                    return repository.save(carPark);

                })
                .orElseGet(() -> {
                    newCarPark.setId(id);
                    return repository.save(newCarPark);
                });
    }

    @DeleteMapping("/carparks/{id}")
    void deleteCarPark(@PathVariable Long id) {
        repository.deleteById(id);
    }





}

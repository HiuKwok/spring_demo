package com.hf.spring.demo.web;

import com.hf.spring.demo.model.CarPark;
import com.hf.spring.demo.repository.CarParkRepository;
import com.hf.spring.demo.utils.CarParkNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    CollectionModel<EntityModel<CarPark>> all() {

        List<EntityModel<CarPark>> carparks = repository.findAll().stream()
                .map(carpark -> EntityModel.of(carpark,
                        linkTo(methodOn(CarParkController.class).one(carpark.getId())).withSelfRel(),
                        linkTo(methodOn(CarParkController.class).all()).withRel("carparks")))
                            .collect(Collectors.toList());
        return CollectionModel.of(carparks,
                linkTo(methodOn(CarParkController.class).all()).withSelfRel());
    }

    @PostMapping("/carparks")
    CarPark newCarPark(@RequestBody CarPark newCarPark) {
        return repository.save(newCarPark);
    }

    @GetMapping("/carparks/{id}")
    EntityModel<CarPark> one(@PathVariable Long id) {

        CarPark carpark = repository.findById(id)
                .orElseThrow(() -> new CarParkNotFoundException(id));

        return EntityModel.of(carpark,
            linkTo(methodOn(CarParkController.class).one(id)).withSelfRel(),
            linkTo(methodOn(CarParkController.class).all()).withRel("carparks")
        );
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

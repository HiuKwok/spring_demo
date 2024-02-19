package com.hf.spring.demo.web;

import com.hf.spring.demo.model.CarPark;
import com.hf.spring.demo.model.CarParkModelAssembler;
import com.hf.spring.demo.repository.CarParkRepository;
import com.hf.spring.demo.utils.CarParkNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
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

    private final CarParkModelAssembler assembler;

    public CarParkController(CarParkRepository repository, CarParkModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/carparks")
    public CollectionModel<EntityModel<CarPark>> all() {

        List<EntityModel<CarPark>> carparks = repository.findAll().stream()
                // Old of way of the conversion.
                //                .map(carpark -> EntityModel.of(carpark,
                //                        linkTo(methodOn(CarParkController.class).one(carpark.getId())).withSelfRel(),
                //                        linkTo(methodOn(CarParkController.class).all()).withRel("carparks")))
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(carparks,
                linkTo(methodOn(CarParkController.class).all()).withSelfRel());
    }

    @PostMapping("/carparks")
    ResponseEntity<EntityModel<CarPark>> newCarPark(@RequestBody CarPark newCarPark) {

        EntityModel<CarPark> em = assembler.toModel(repository.save(newCarPark));
        // ResponseEntity used to return 201 Created message with Location response header.
        return ResponseEntity
                .created(em.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(em);
    }

    @GetMapping("/carparks/{id}")
    public EntityModel<CarPark> one(@PathVariable Long id) {

        CarPark carpark = repository.findById(id)
                .orElseThrow(() -> new CarParkNotFoundException(id));

        return assembler.toModel(carpark);
        //      The old way of modeling item without Assembler.
        //        return EntityModel.of(carpark,
        //            linkTo(methodOn(CarParkController.class).one(id)).withSelfRel(),
        //            linkTo(methodOn(CarParkController.class).all()).withRel("carparks")
        //        );
    }

    @PutMapping("/carparks/{id}")
    ResponseEntity<?> replaceCarPark(@RequestBody CarPark newCarPark,
                           @PathVariable Long id) {

        CarPark updatedCarpark =  repository.findById(id)
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

        EntityModel<CarPark> em = assembler.toModel(updatedCarpark);
        return ResponseEntity
                .created(em.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(em);
    }

    @DeleteMapping("/carparks/{id}")
    ResponseEntity<?> deleteCarPark(@PathVariable Long id) {

        repository.deleteById(id);
        // To return 204 No Content.
        return ResponseEntity.noContent().build();

    }





}

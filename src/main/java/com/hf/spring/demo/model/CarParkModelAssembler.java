package com.hf.spring.demo.model;

import com.hf.spring.demo.web.CarParkController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * To add hypermedia-powered content,
 * see: https://spring.io/guides/tutorials/rest
 */
@Component
public class CarParkModelAssembler implements RepresentationModelAssembler<CarPark, EntityModel<CarPark>> {

    @Override
    public EntityModel<CarPark> toModel(CarPark carpark) {
        return EntityModel.of(carpark,
                linkTo(methodOn(CarParkController.class).one(carpark.getId())).withSelfRel(),
                linkTo(methodOn(CarParkController.class).all()).withRel("carparks"));

    }
}

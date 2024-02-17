package com.hf.spring.demo.utils;

public class CarParkNotFoundException extends RuntimeException{

    CarParkNotFoundException(Long id) {
        super("Could not find employee " + id);
    }

}

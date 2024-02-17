package com.hf.spring.demo.repository;

import com.hf.spring.demo.model.CarPark;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Only declaring the interface works.
 */
public interface CarParkRepository extends JpaRepository<CarPark, Long> {

}

package test.gai.repository;

import test.gai.entity.Car;
import test.gai.entity.Owner;

import java.util.List;
import java.util.Optional;

public interface CarRepository {

    List<Car> findAll();

    Optional<Car> findById(Long id);

    Car save(Car car);

    void deleteById(Long id);

    List<Car> findByOwnerId(Long id);
}
package test.gai.repository;

import test.gai.model.Car;
import test.gai.model.Owner;

import java.util.List;
import java.util.Optional;

public interface CarRepository {

    List<Car> findAll();

    Optional<Car> findById(Long id);

    Car save(Car car);

    void deleteById(Long id);

    List<Car> findByOwner(Owner owner);
}
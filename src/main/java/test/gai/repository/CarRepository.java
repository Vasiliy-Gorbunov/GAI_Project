package test.gai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.gai.model.Car;

public interface CarRepository extends JpaRepository<Car, Long> {

}

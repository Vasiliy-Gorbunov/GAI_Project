package test.gai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.gai.entity.Car;
import test.gai.entity.Owner;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarJpaRepository extends CarRepository, JpaRepository<Car, Long> {

    List<Car> findAll();

    Optional<Car> findById(Long id);

    Car save(Car car);

    void deleteById(Long id);

    List<Car> findByOwnerId(Long id);
}

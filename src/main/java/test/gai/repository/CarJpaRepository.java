package test.gai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.gai.entity.Car;
import test.gai.entity.Owner;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarJpaRepository extends CarRepository, JpaRepository<Car, Long> {

    @Override
    List<Car> findAll();

    @Override
    Optional<Car> findById(Long id);

    @Override
    Car save(Car car);

    @Override
    void deleteById(Long id);

    List<Car> findByOwner(Owner owner);
}

package test.gai.repository;

import test.gai.model.Owner;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import test.gai.mapper.CarRowMapper;
import test.gai.model.Car;

import java.util.List;
import java.util.Optional;

@Repository
public class CarJdbcRepository implements CarRepository {

    private final JdbcTemplate jdbcTemplate;

    public CarJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Car> findAll() {
        String sql = "SELECT * FROM car";
        return jdbcTemplate.query(sql, new CarRowMapper());
    }

    @Override
    public Optional<Car> findById(Long id) {
        String sql = "SELECT * FROM car WHERE id = ?";
        List<Car> cars = jdbcTemplate.query(sql, new Object[]{id}, new CarRowMapper());
        return cars.stream().findFirst();
    }

    @Override
    public Car save(Car car) {
        if (car.getId() == null) {
            String sql = "INSERT INTO car (make, model, number_plate, owner_id) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    car.getMake(),
                    car.getModel(),
                    car.getNumberPlate(),
                    car.getOwner().getId());
        } else {
            String sql = "UPDATE car SET make = ?, model = ?, number_plate = ?, owner_id = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    car.getMake(),
                    car.getModel(),
                    car.getNumberPlate(),
                    car.getOwner().getId(),
                    car.getId());
        }
        return car;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM car WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // Получение автомобилей по ID владельца
    @Transactional(readOnly = true)
    public List<Car> findByOwner(Owner owner) {
        String sql = "SELECT * FROM car WHERE owner_id = ?";
        return jdbcTemplate.query(sql, new Object[]{owner.getId()}, new CarRowMapper());
    }
}

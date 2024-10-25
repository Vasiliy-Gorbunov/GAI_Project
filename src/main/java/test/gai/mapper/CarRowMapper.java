package test.gai.mapper;

import org.springframework.jdbc.core.RowMapper;
import test.gai.entity.Car;
import test.gai.entity.Owner;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarRowMapper implements RowMapper<Car> {

    @Override
    public Car mapRow(ResultSet rs, int rowNum) throws SQLException {
        Car car = new Car();
        car.setId(rs.getLong("id"));
        car.setMake(rs.getString("make"));
        car.setModel(rs.getString("model"));
        car.setNumberPlate(rs.getString("number_plate"));

        Long ownerId = rs.getLong("owner_id");
        if (!rs.wasNull()) {
            Owner owner = new Owner();
            owner.setId(ownerId);
            car.setOwner(owner);
        }

        return car;
    }
}

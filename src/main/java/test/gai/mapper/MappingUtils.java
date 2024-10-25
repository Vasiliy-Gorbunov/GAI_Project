package test.gai.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.gai.DTO.CarDto;
import test.gai.DTO.OwnerDto;
import test.gai.entity.Car;
import test.gai.entity.Owner;
import test.gai.model.CarModel;
import test.gai.model.OwnerModel;

@Service
public class MappingUtils {

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private OwnerMapper ownerMapper;

    public CarDto mapToCarDto(CarModel model) {
        return carMapper.toCarDto(model);
    }

    public OwnerDto mapToOwnerDto(OwnerModel model) {
        return ownerMapper.toOwnerDto(model);
    }

    public CarModel mapToCarModelFromDto(CarDto dto) {
        return carMapper.toCarModelFromDto(dto);
    }

    public OwnerModel mapToOwnerModelFromDto(OwnerDto dto) {
        return ownerMapper.toOwnerModelFromDto(dto);
    }

    public CarModel mapToCarModelFromEntity(Car car) {
        return carMapper.toCarModelFromEntity(car);
    }

    public OwnerModel mapToOwnerModelFromEntity(Owner owner) {
        return ownerMapper.toOwnerModelFromEntity(owner);
    }

    public Car mapToCar(CarModel model) {
        return carMapper.toCar(model);
    }

    public Owner mapToOwner(OwnerModel model) {
        return ownerMapper.toOwner(model);
    }
}

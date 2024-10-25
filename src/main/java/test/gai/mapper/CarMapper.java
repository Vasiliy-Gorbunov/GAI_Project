package test.gai.mapper;

import org.mapstruct.*;
import test.gai.DTO.CarDto;
import test.gai.entity.Car;
import test.gai.model.CarModel;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OwnerMapper.class})
public interface CarMapper {

    CarDto toCarDto(CarModel model);

    CarModel toCarModelFromDto(CarDto dto);

    CarModel toCarModelFromEntity(Car car);

    Car toCar(CarModel model);

    // Маппинг списка машин
    List<CarDto> toCarDtoList(List<CarModel> carModels);

    List<CarModel> toCarModelListFromDto(List<CarDto> carDtos);

    List<CarModel> toCarModelListFromEntity(List<Car> cars);

    List<Car> toCarList(List<CarModel> carModels);
}

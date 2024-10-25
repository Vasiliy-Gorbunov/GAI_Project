package test.gai.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import test.gai.DTO.CarDto;
import test.gai.mapper.MappingUtils;
import test.gai.model.CarModel;
import test.gai.service.CarService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/cars")
public class CarController {

    private final CarService carService;
    private final MappingUtils mappingUtils;

    @Autowired
    public CarController(CarService carService, MappingUtils mappingUtils) {
        this.carService = carService;
        this.mappingUtils = mappingUtils;
    }

    @GetMapping
    public List<CarDto> getAllCars() {
        return carService.getAllCars().stream()
                .map(mappingUtils::mapToCarDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CarDto getCarById(@PathVariable Long id) {
        return mappingUtils.mapToCarDto(carService.getCarById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CarDto createCar(@Valid @RequestBody CarDto carDto) {
        CarModel carModel = mappingUtils.mapToCarModelFromDto(carDto);
        return mappingUtils.mapToCarDto(carService.createCar(carModel));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CarDto updateCar(@PathVariable Long id, @Valid @RequestBody CarDto carDto) {
        CarModel updatedCar = mappingUtils.mapToCarModelFromDto(carDto);
        return mappingUtils.mapToCarDto(carService.updateCar(id, updatedCar));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
    }

    @GetMapping("/owner/{ownerId}")
    public List<CarDto> getCarsByOwnerId(@PathVariable Long ownerId) {
        return carService.getCarsByOwnerId(ownerId).stream()
                .map(mappingUtils::mapToCarDto)
                .collect(Collectors.toList());
    }
}
package test.gai.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import test.gai.model.Car;
import test.gai.service.CarService;

import java.util.List;


@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    public Car getCarById(@PathVariable Long id) {
        return carService.getCarById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Car createCar(@Valid @RequestBody Car car) {
        return carService.createCar(car);
    }

    @PutMapping("/{id}")
    public Car updateCar(@PathVariable Long id, @Valid @RequestBody Car updatedCar) {
        return carService.updateCar(id, updatedCar);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
    }

    @GetMapping("/owner/{ownerId}")
    public List<Car> getCarsByOwnerId(@PathVariable Long ownerId) {
        return carService.getCarsByOwnerId(ownerId);
    }
}
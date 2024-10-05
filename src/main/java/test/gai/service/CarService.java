package test.gai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.gai.exception.ResourceNotFoundException;
import test.gai.model.Car;
import test.gai.model.Owner;
import test.gai.repository.CarRepository;
import test.gai.repository.OwnerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final OwnerRepository ownerRepository;

    @Autowired
    public CarService(CarRepository carRepository, OwnerRepository ownerRepository) {
        this.carRepository = carRepository;
        this.ownerRepository = ownerRepository;
    }


    @Transactional(readOnly = true)
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }


    @Transactional(readOnly = true)
    public Car getCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car with id " + id + " not found"));
    }


    @Transactional
    public Car createCar(Car car) {
        if (car.getOwner() != null) {
            Optional<Owner> owner = ownerRepository.findById(car.getOwner().getId());
            if (owner.isEmpty()) {
                throw new ResourceNotFoundException("Owner with id " + car.getOwner().getId() + " not found");
            }
            car.setOwner(owner.get());
        }
        return carRepository.save(car);
    }


    @Transactional
    public Car updateCar(Long id, Car updatedCar) {
        Car existingCar = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car with id " + id + " not found"));

        existingCar.setMake(updatedCar.getMake());
        existingCar.setModel(updatedCar.getModel());
        existingCar.setNumberPlate(updatedCar.getNumberPlate());

        if (updatedCar.getOwner() != null) {
            Optional<Owner> owner = ownerRepository.findById(updatedCar.getOwner().getId());
            if (owner.isEmpty()) {
                throw new ResourceNotFoundException("Owner with id " + updatedCar.getOwner().getId() + " not found");
            }
            existingCar.setOwner(owner.get());
        }

        return carRepository.save(existingCar);
    }


    @Transactional
    public void deleteCar(Long id) {
        Car existingCar = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car with id " + id + " not found"));
        carRepository.deleteById(existingCar.getId());
    }


    @Transactional(readOnly = true)
    public List<Car> getCarsByOwnerId(Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner with id " + ownerId + " not found"));
        return carRepository.findByOwner(owner);
    }
}
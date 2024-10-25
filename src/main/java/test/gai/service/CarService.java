package test.gai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.gai.exception.ResourceNotFoundException;
import test.gai.entity.Car;
import test.gai.entity.Owner;
import test.gai.mapper.MappingUtils;
import test.gai.model.CarModel;
import test.gai.repository.CarRepository;
import test.gai.repository.OwnerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final OwnerRepository ownerRepository;
    private final MappingUtils mappingUtils;

    @Autowired
    public CarService(CarRepository carRepository, OwnerRepository ownerRepository, MappingUtils mappingUtils) {
        this.carRepository = carRepository;
        this.ownerRepository = ownerRepository;
        this.mappingUtils = mappingUtils;
    }


    @Transactional(readOnly = true)
    public List<CarModel> getAllCars() {
        return carRepository.findAll().stream().map(mappingUtils::mapToCarModelFromEntity).collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public CarModel getCarById(Long id) {
        return mappingUtils.mapToCarModelFromEntity(carRepository.findById(id)
                .orElseThrow(() -> ThrowableMessage("Car", id)));
    }


    @Transactional
    public CarModel createCar(CarModel carModel) {
        Car car = mappingUtils.mapToCar(carModel);
        if (car.getOwner() != null) {
            Optional<Owner> owner = ownerRepository.findById(car.getOwner().getId());
            if (owner.isEmpty()) {
                throw ThrowableMessage("Car", car.getOwner().getId());
            }
            car.setOwner(owner.get());
        }
        return mappingUtils.mapToCarModelFromEntity(carRepository.save(car));
    }


    @Transactional
    public CarModel updateCar(Long id, CarModel updatedCar) {
        Car existingCar = carRepository.findById(id)
                .orElseThrow(() -> ThrowableMessage("Car", id));

        Car updatingCar = mappingUtils.mapToCar(updatedCar);

        existingCar.setMake(updatingCar.getMake());
        existingCar.setModel(updatingCar.getModel());
        existingCar.setNumberPlate(updatingCar.getNumberPlate());

        if (updatingCar.getOwner() != null) {
            Optional<Owner> owner = ownerRepository.findById(updatingCar.getOwner().getId());
            if (owner.isEmpty()) {
                throw ThrowableMessage("Owner", updatingCar.getOwner().getId());
            }
            existingCar.setOwner(owner.get());
        }

        return mappingUtils.mapToCarModelFromEntity(carRepository.save(existingCar));
    }


    @Transactional
    public void deleteCar(Long id) {
        Car existingCar = carRepository.findById(id)
                .orElseThrow(() -> ThrowableMessage("Car", id));
        carRepository.deleteById(existingCar.getId());
    }


    @Transactional(readOnly = true)
    public List<CarModel> getCarsByOwnerId(Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> ThrowableMessage("Owner", ownerId));
        return carRepository.findByOwner(owner).stream()
                .map(mappingUtils::mapToCarModelFromEntity)
                .collect(Collectors.toList());
    }

    private ResourceNotFoundException ThrowableMessage(String obj, Long id) {
        return new ResourceNotFoundException(obj + " with id " + id + " not found");
    }
}
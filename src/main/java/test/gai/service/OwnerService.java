package test.gai.service;

import org.springframework.stereotype.Service;
import test.gai.exception.ResourceNotFoundException;
import test.gai.entity.Owner;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import test.gai.mapper.MappingUtils;
import test.gai.model.OwnerModel;
import test.gai.repository.CarRepository;
import test.gai.repository.OwnerRepository;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final CarRepository carRepository;
    private final MappingUtils mappingUtils;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository, CarRepository carRepository, MappingUtils mappingUtils) {
        this.ownerRepository = ownerRepository;
        this.carRepository = carRepository;
        this.mappingUtils = mappingUtils;
    }


    @Transactional(readOnly = true)
    public List<OwnerModel> getAllOwners() {
        return ownerRepository.findAll().stream()
                .map(mappingUtils::mapToOwnerModelFromEntity)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public OwnerModel getOwnerById(Long id) {
        OwnerModel ownerModel = mappingUtils
                .mapToOwnerModelFromEntity(ownerRepository.findById(id)
                        .orElseThrow(() -> ThrowableMessage("Owner", id)));
        ownerModel.setCars(carRepository.findByOwnerId(id)
                .stream()
                .map(mappingUtils::mapToCarModelFromEntity)
                .toList());
        return ownerModel;
    }


    @Transactional
    public OwnerModel createOwner(OwnerModel ownerModel) {
        Owner owner = mappingUtils.mapToOwner(ownerModel);
        return mappingUtils.mapToOwnerModelFromEntity(ownerRepository.save(owner));
    }


    @Transactional
    public OwnerModel updateOwner(Long id, OwnerModel updatedOwner) {
        Owner existingOwner = ownerRepository.findById(id)
                .orElseThrow(() -> ThrowableMessage("Owner", id));

        mappingUtils.mapToOwner(updatedOwner);
        existingOwner.setName(updatedOwner.getName());
        existingOwner.setDob(updatedOwner.getDob());
        existingOwner.setGender(updatedOwner.getGender());
        existingOwner.setLicenseCategories(updatedOwner.getLicenseCategories());

        return mappingUtils.mapToOwnerModelFromEntity(ownerRepository.save(existingOwner));
    }


    @Transactional
    public void deleteOwner(Long id) {
        Owner existingOwner = ownerRepository.findById(id)
                .orElseThrow(() -> ThrowableMessage("Car", id));
        ownerRepository.deleteById(existingOwner.getId());
    }

    private ResourceNotFoundException ThrowableMessage(String obj, Long id) {
        return new ResourceNotFoundException(obj + " with id " + id + " not found");
    }
}


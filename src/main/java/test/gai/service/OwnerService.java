package test.gai.service;

import org.springframework.stereotype.Service;
import test.gai.exception.ResourceNotFoundException;
import test.gai.model.Owner;
import test.gai.repository.OwnerJpaRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OwnerService {

    private final OwnerJpaRepository ownerRepository;

    @Autowired
    public OwnerService(OwnerJpaRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }


    @Transactional(readOnly = true)
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }


    @Transactional(readOnly = true)
    public Owner getOwnerById(Long id) {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner with id " + id + " not found"));
    }


    @Transactional
    public Owner createOwner(Owner owner) {
        return ownerRepository.save(owner);
    }


    @Transactional
    public Owner updateOwner(Long id, Owner updatedOwner) {
        Owner existingOwner = ownerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner with id " + id + " not found"));

        existingOwner.setName(updatedOwner.getName());
        existingOwner.setDob(updatedOwner.getDob());
        existingOwner.setGender(updatedOwner.getGender());
        existingOwner.setLicenseCategories(updatedOwner.getLicenseCategories());

        return ownerRepository.save(existingOwner);
    }


    @Transactional
    public void deleteOwner(Long id) {
        Owner existingOwner = ownerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner with id " + id + " not found"));
        ownerRepository.deleteById(existingOwner.getId());
    }
}


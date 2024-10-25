package test.gai.repository;

import test.gai.entity.Owner;

import java.util.List;
import java.util.Optional;

public interface OwnerRepository {

    List<Owner> findAll();

    Optional<Owner> findById(Long id);

    Owner save(Owner owner);

    void deleteById(Long id);
}
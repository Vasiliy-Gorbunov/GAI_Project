package test.gai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.gai.model.Owner;

import java.util.List;
import java.util.Optional;

@Repository
public interface OwnerJpaRepository extends OwnerRepository, JpaRepository<Owner, Long> {

    @Override
    List<Owner> findAll();

    @Override
    Optional<Owner> findById(Long id);

    @Override
    Owner save(Owner owner);

    @Override
    void deleteById(Long id);
}

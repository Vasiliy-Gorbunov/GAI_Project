package test.gai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.gai.model.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}

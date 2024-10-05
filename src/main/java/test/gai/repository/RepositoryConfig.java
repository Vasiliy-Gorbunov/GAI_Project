package test.gai.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.beans.factory.annotation.Value;


@Configuration
public class RepositoryConfig {

    @Value("${repository.type}")
    private String repositoryType;

    @Autowired
    private CarJpaRepository carJpaRepository;
    @Autowired
    private CarJdbcRepository carJdbcRepository;
    @Autowired
    private OwnerJpaRepository ownerJpaRepository;
    @Autowired
    private OwnerJdbcRepository ownerJdbcRepository;

    @Bean
    @Primary
    public OwnerRepository ownerRepository() {
        if ("jdbc".equalsIgnoreCase(repositoryType)) {
            System.out.println("JDBC Owner Repository selected.");
            return ownerJdbcRepository;
        } else {
            System.out.println("JPA Owner Repository selected.");
            return ownerJpaRepository;
        }
    }

    @Bean
    @Primary
    public CarRepository carRepository() {
        if ("jdbc".equalsIgnoreCase(repositoryType)) {
            System.out.println("JDBC Car Repository selected.");
            return carJdbcRepository;
        } else {
            System.out.println("JPA Car Repository selected.");
            return carJpaRepository;
        }
    }
}
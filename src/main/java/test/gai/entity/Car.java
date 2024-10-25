package test.gai.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String make;
    private String model;
    private String numberPlate;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonBackReference // Указывает, что это "дочерняя" часть связи
    private Owner owner;
}

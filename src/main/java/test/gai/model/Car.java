package test.gai.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Make cannot be null")
    private String make;

    @NotNull(message = "Model cannot be null")
    private String model;

    @NotNull(message = "Number plate cannot be null")
    private String numberPlate;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonBackReference // Указывает, что это "дочерняя" часть связи
    @NotNull(message = "Owner cannot be null")
    private Owner owner;
}

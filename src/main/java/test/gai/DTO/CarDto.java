package test.gai.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class CarDto {
    private Long id;

    @NotNull(message = "Make cannot be null")
    private String make;

    @NotNull(message = "Model cannot be null")
    private String model;

    @NotNull(message = "Number plate cannot be null")
    @Size(min = 3, max = 20, message = "Number plate must be between 3 and 20 characters")
    private String numberPlate;

    private OwnerDto owner;
}

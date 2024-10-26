package test.gai.DTO;

import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class CarDto {

    public interface BasicView {}

    @JsonView(BasicView.class)
    private Long id;

    @JsonView(BasicView.class)
    @NotNull(message = "Make cannot be null")
    private String make;

    @JsonView(BasicView.class)
    @NotNull(message = "Model cannot be null")
    private String model;

    @JsonView(BasicView.class)
    @NotNull(message = "Number plate cannot be null")
    @Size(min = 3, max = 20, message = "Number plate must be between 3 and 20 characters")
    private String numberPlate;

    @JsonView(BasicView.class)
    private OwnerDto owner;
}

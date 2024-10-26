package test.gai.DTO;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import test.gai.entity.Gender;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Component
public class OwnerDto {

    public interface BasicView {} // Представление для ограниченного набора полей

    @JsonView(BasicView.class)
    private Long id;

    @JsonView(BasicView.class)
    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @JsonView(BasicView.class)
    @NotNull(message = "Date of birth cannot be null")
    @Past(message = "Date of birth must be in the past")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dob;

    @JsonView(BasicView.class)
    @NotNull(message = "Gender cannot be null")
    private Gender gender;

    @JsonView(BasicView.class)
    @NotBlank(message = "License categories cannot be blank")
    private String licenseCategories;

    private List<CarDto> cars;
}

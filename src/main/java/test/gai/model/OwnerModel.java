package test.gai.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import test.gai.entity.Gender;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Component
public class OwnerModel {
    private Long id;
    private String name;
    private LocalDate dob;
    private Gender gender;
    private String licenseCategories;
    private List<CarModel> cars;
}

package test.gai.model;

import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@Component
public class CarModel {

    private Long id;
    private String make;
    private String model;
    private String numberPlate;
    private OwnerModel owner;
}

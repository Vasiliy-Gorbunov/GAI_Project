package test.gai.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import test.gai.model.Car;
import test.gai.service.CarService;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Test
    public void whenValidCreateCar_thenReturns201() throws Exception {
        String validCarJson = """
                {
                    "make": "Toyota",
                    "model": "Camry",
                    "numberPlate": "A123BC",
                    "owner": { "id": 1 }
                }
                """;

        when(carService.createCar(any(Car.class))).thenReturn(new Car());

        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validCarJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void whenInvalidMake_thenReturns400() throws Exception {
        String invalidCarJson = """
                {
                    "model": "Camry",
                    "numberPlate": "A123BC",
                    "owner": { "id": 1 }
                }
                """;

        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidCarJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Make cannot be null")));
    }

    @Test
    public void whenInvalidNumberPlate_thenReturns400() throws Exception {
        String invalidCarJson = """
                {
                    "make": "Toyota",
                    "model": "Camry",
                    "owner": { "id": 1 }
                }
                """;

        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidCarJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Number plate cannot be null")));
    }

    @Test
    public void whenMissingOwner_thenReturns400() throws Exception {
        String invalidCarJson = """
                {
                    "make": "Toyota",
                    "model": "Camry",
                    "numberPlate": "A123BC"
                }
                """;

        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidCarJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Owner cannot be null")));
    }

    @Test
    public void whenValidCarUpdate_thenReturns200() throws Exception {
        String validCarJson = """
                {
                    "make": "Toyota",
                    "model": "Camry",
                    "numberPlate": "A123BC",
                    "owner": { "id": 1 }
                }
                """;

        when(carService.updateCar(eq(1L), any(Car.class))).thenReturn(new Car());

        mockMvc.perform(put("/cars/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validCarJson))
                .andExpect(status().isOk());
    }

    @Test
    public void whenInvalidMakeUpdate_thenReturns400() throws Exception {
        String invalidCarJson = """
                {
                    "model": "Camry",
                    "numberPlate": "A123BC",
                    "owner": { "id": 1 }
                }
                """;

        mockMvc.perform(put("/cars/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidCarJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Make cannot be null")));
    }

    @Test
    public void whenInvalidModelUpdate_thenReturns400() throws Exception {
        String invalidCarJson = """
                {
                    "make": "Toyota",
                    "numberPlate": "A123BC",
                    "owner": { "id": 1 }
                }
                """;

        mockMvc.perform(put("/cars/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidCarJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Model cannot be null")));
    }

    @Test
    public void whenInvalidNumberPlateUpdate_thenReturns400() throws Exception {
        String invalidCarJson = """
                {
                    "make": "Toyota",
                    "model": "Camry",
                    "owner": { "id": 1 }
                }
                """;

        mockMvc.perform(put("/cars/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidCarJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Number plate cannot be null")));
    }
}

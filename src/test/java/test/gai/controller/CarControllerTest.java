package test.gai.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import test.gai.DTO.CarDto;
import test.gai.mapper.MappingUtils;
import test.gai.model.CarModel;
import test.gai.service.CarService;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @MockBean
    private MappingUtils mappingUtils;

    private CarDto carDto;
    private CarModel carModel;

    @BeforeEach
    void setUp() {
        carDto = new CarDto();
        carDto.setId(1L);
        carDto.setMake("Ford");
        carDto.setModel("Focus");
        carDto.setNumberPlate("LMN456");

        carModel = new CarModel();
        carModel.setId(1L);
        carModel.setMake("Ford");
        carModel.setModel("Focus");
        carModel.setNumberPlate("LMN456");
    }

    @Test
    void getAllCars() throws Exception {
        when(carService.getAllCars()).thenReturn(Collections.singletonList(carModel));
        when(mappingUtils.mapToCarDto(any(CarModel.class))).thenReturn(carDto);

        mockMvc.perform(get("/cars")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].make").value("Ford"))
                .andExpect(jsonPath("$[0].model").value("Focus"))
                .andExpect(jsonPath("$[0].numberPlate").value("LMN456"));
    }

    @Test
    void getCarById() throws Exception {
        when(carService.getCarById(1L)).thenReturn(carModel);
        when(mappingUtils.mapToCarDto(any(CarModel.class))).thenReturn(carDto);

        mockMvc.perform(get("/cars/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.make").value("Ford"))
                .andExpect(jsonPath("$.model").value("Focus"))
                .andExpect(jsonPath("$.numberPlate").value("LMN456"));
    }

    @Test
    void createCar() throws Exception {
        when(mappingUtils.mapToCarModelFromDto(any(CarDto.class))).thenReturn(carModel);
        when(carService.createCar(any(CarModel.class))).thenReturn(carModel);
        when(mappingUtils.mapToCarDto(any(CarModel.class))).thenReturn(carDto);

        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(carDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.make").value("Ford"))
                .andExpect(jsonPath("$.model").value("Focus"))
                .andExpect(jsonPath("$.numberPlate").value("LMN456"));
    }

    @Test
    void updateCar() throws Exception {
        when(mappingUtils.mapToCarModelFromDto(any(CarDto.class))).thenReturn(carModel);
        when(carService.updateCar(any(Long.class), any(CarModel.class))).thenReturn(carModel);
        when(mappingUtils.mapToCarDto(any(CarModel.class))).thenReturn(carDto);

        mockMvc.perform(put("/cars/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(carDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.make").value("Ford"))
                .andExpect(jsonPath("$.model").value("Focus"))
                .andExpect(jsonPath("$.numberPlate").value("LMN456"));
    }

    @Test
    void deleteCar() throws Exception {
        mockMvc.perform(delete("/cars/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getCarsByOwnerId() throws Exception {
        when(carService.getCarsByOwnerId(1L)).thenReturn(List.of(carModel));
        when(mappingUtils.mapToCarDto(any(CarModel.class))).thenReturn(carDto);

        mockMvc.perform(get("/cars/owner/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].make").value("Ford"))
                .andExpect(jsonPath("$[0].model").value("Focus"))
                .andExpect(jsonPath("$[0].numberPlate").value("LMN456"));
    }
}

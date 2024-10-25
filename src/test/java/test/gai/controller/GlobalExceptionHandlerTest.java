package test.gai.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import test.gai.service.CarService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {CarController.class, OwnerController.class, GlobalExceptionHandler.class})
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarController carController;

    @MockBean
    private OwnerController ownerController;

    @MockBean
    private CarService carService;

    @Test
    void handleResourceNotFoundException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/999")) // Неправильный путь для теста
                .andExpect(status().isNotFound())
                .andExpect(content().string("Car with id 999 not found"));
    }

    @Test
    void handleValidationExceptions() throws Exception {
        // Попытка создания объекта с некорректными данными
        String invalidOwnerDto = "{ \"id\": null, \"name\": \"\", \"dob\": \"2025-10-25\", \"gender\": null, \"licenseCategories\": \"\" }";

        mockMvc.perform(MockMvcRequestBuilders.post("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidOwnerDto))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":\"Id cannot be null\",\"name\":\"Name must be between 2 and 100 characters\",\"dob\":\"Date of birth must be in the past\",\"gender\":\"Gender cannot be null\",\"licenseCategories\":\"License categories cannot be null\"}"));
    }
}

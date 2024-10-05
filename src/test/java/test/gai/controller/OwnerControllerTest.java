package test.gai.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import test.gai.model.Owner;
import test.gai.service.OwnerService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OwnerController.class)
public class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerService ownerService;

    @Test
    public void whenValidOwner_thenReturns201() throws Exception {
        String validOwnerJson = """
                {
                    "name": "John Doe",
                    "dob": "1980-01-01",
                    "gender": "MALE",
                    "licenseCategories": "B, C"
                }
                """;

        when(ownerService.createOwner(any(Owner.class))).thenReturn(new Owner());

        mockMvc.perform(post("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validOwnerJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void whenValidOwnerUpdate_thenReturns200() throws Exception {
        String validOwnerJson = """
                {
                    "name": "John Doe",
                    "dob": "1980-01-01",
                    "gender": "MALE",
                    "licenseCategories": "B, C"
                }
                """;

        // Настраиваем мок, чтобы он возвращал обновлённый объект Owner
        when(ownerService.updateOwner(eq(1L), any(Owner.class))).thenReturn(new Owner());

        mockMvc.perform(put("/owners/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validOwnerJson))
                .andExpect(status().isOk());
    }

    @Test
    public void whenInvalidOwnerName_thenReturns400() throws Exception {
        String invalidOwnerJson = """
                {
                    "name": "J",
                    "dob": "1980-01-01",
                    "gender": "MALE",
                    "licenseCategories": "B, C"
                }
                """;

        mockMvc.perform(post("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidOwnerJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Name must be between 2 and 100 characters")));
    }

    @Test
    public void whenMissingGender_thenReturns400() throws Exception {
        String invalidOwnerJson = """
                {
                    "name": "John Doe",
                    "dob": "1980-01-01",
                    "licenseCategories": "B, C"
                }
                """;

        mockMvc.perform(post("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidOwnerJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Gender cannot be null")));
    }

    @Test
    public void whenInvalidDOB_thenReturns400() throws Exception {
        String invalidOwnerJson = """
                {
                    "name": "John Doe",
                    "dob": "2030-01-01",
                    "gender": "MALE",
                    "licenseCategories": "B, C"
                }
                """;

        mockMvc.perform(post("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidOwnerJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Date of birth must be in the past")));
    }

    @Test
    public void whenInvalidLicenseCategories_thenReturns400() throws Exception {
        String invalidOwnerJson = """
                {
                    "name": "John Doe",
                    "dob": "1980-01-01",
                    "gender": "MALE",
                    "licenseCategories": ""
                }
                """;

        mockMvc.perform(post("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidOwnerJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("License categories cannot be null")));
    }

    @Test
    public void whenInvalidOwnerNameUpdate_thenReturns400() throws Exception {
        String invalidOwnerJson = """
                {
                    "name": "J",
                    "dob": "1980-01-01",
                    "gender": "MALE",
                    "licenseCategories": "B, C"
                }
                """;

        mockMvc.perform(put("/owners/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidOwnerJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Name must be between 2 and 100 characters")));
    }

    @Test
    public void whenMissingGenderUpdate_thenReturns400() throws Exception {
        String invalidOwnerJson = """
                {
                    "name": "John Doe",
                    "dob": "1980-01-01",
                    "licenseCategories": "B, C"
                }
                """;

        mockMvc.perform(put("/owners/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidOwnerJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Gender cannot be null")));
    }

    @Test
    public void whenInvalidDobUpdate_thenReturns400() throws Exception {
        String invalidOwnerJson = """
                {
                    "name": "John Doe",
                    "dob": "2030-01-01",
                    "gender": "MALE",
                    "licenseCategories": "B, C"
                }
                """;

        mockMvc.perform(put("/owners/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidOwnerJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Date of birth must be in the past")));
    }

    @Test
    public void whenInvalidLicenseCategoriesUpdate_thenReturns400() throws Exception {
        String invalidOwnerJson = """
                {
                    "name": "John Doe",
                    "dob": "1980-01-01",
                    "gender": "MALE",
                    "licenseCategories": ""
                }
                """;

        mockMvc.perform(put("/owners/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidOwnerJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("License categories cannot be null")));
    }
}


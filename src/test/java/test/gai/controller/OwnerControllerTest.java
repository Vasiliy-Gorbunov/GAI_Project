package test.gai.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import test.gai.DTO.OwnerDto;
import test.gai.entity.Gender;
import test.gai.mapper.MappingUtils;
import test.gai.model.OwnerModel;
import test.gai.service.OwnerService;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OwnerController.class)
public class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerService ownerService;

    @MockBean
    private MappingUtils mappingUtils;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAllOwners() throws Exception {
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setId(1L);
        ownerDto.setName("John Doe");
        ownerDto.setDob(LocalDate.of(1990, 1, 1));
        ownerDto.setGender(Gender.MALE);
        ownerDto.setLicenseCategories("B");

        when(ownerService.getAllOwners()).thenReturn(Collections.singletonList(new OwnerModel()));
        when(mappingUtils.mapToOwnerDto(any())).thenReturn(ownerDto);

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    void getOwnerById() throws Exception {
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setId(1L);
        ownerDto.setName("John Doe");
        ownerDto.setDob(LocalDate.of(1990, 1, 1));
        ownerDto.setGender(Gender.MALE);
        ownerDto.setLicenseCategories("B");

        when(ownerService.getOwnerById(1L)).thenReturn(new OwnerModel());
        when(mappingUtils.mapToOwnerDto(any())).thenReturn(ownerDto);

        mockMvc.perform(get("/owners/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void createOwner() throws Exception {
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setId(1L);
        ownerDto.setName("John Doe");
        ownerDto.setDob(LocalDate.of(1990, 1, 1));
        ownerDto.setGender(Gender.MALE);
        ownerDto.setLicenseCategories("B");

        OwnerModel ownerModel = new OwnerModel();
        ownerModel.setId(1L);
        ownerModel.setName("John Doe");

        when(mappingUtils.mapToOwnerModelFromDto(any())).thenReturn(ownerModel);
        when(ownerService.createOwner(any())).thenReturn(ownerModel);
        when(mappingUtils.mapToOwnerDto(any())).thenReturn(ownerDto);

        mockMvc.perform(post("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ownerDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void updateOwner() throws Exception {
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setId(1L);
        ownerDto.setName("John Updated");
        ownerDto.setDob(LocalDate.of(1990, 1, 1));
        ownerDto.setGender(Gender.MALE);
        ownerDto.setLicenseCategories("B");

        OwnerModel ownerModel = new OwnerModel();
        ownerModel.setId(1L);
        ownerModel.setName("John Updated");

        when(mappingUtils.mapToOwnerModelFromDto(any())).thenReturn(ownerModel);
        when(ownerService.updateOwner(anyLong(), any())).thenReturn(ownerModel);
        when(mappingUtils.mapToOwnerDto(any())).thenReturn(ownerDto);

        mockMvc.perform(put("/owners/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ownerDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Updated"));
    }

    @Test
    void deleteOwner() throws Exception {
        Mockito.doNothing().when(ownerService).deleteOwner(1L);

        mockMvc.perform(delete("/owners/1"))
                .andExpect(status().isNoContent());
    }
}

package test.gai.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import test.gai.DTO.OwnerDto;
import test.gai.mapper.MappingUtils;
import test.gai.model.OwnerModel;
import test.gai.service.OwnerService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/owners")
public class OwnerController {


    private final OwnerService ownerService;

    private final MappingUtils mappingUtils;

    public OwnerController(OwnerService ownerService, MappingUtils mappingUtils) {
        this.ownerService = ownerService;
        this.mappingUtils = mappingUtils;
    }


    @GetMapping
    public List<OwnerDto> getAllOwners() {
        return ownerService.getAllOwners().stream()
                .map(mappingUtils::mapToOwnerDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OwnerDto getOwnerById(@PathVariable Long id) {
        return mappingUtils.mapToOwnerDto(ownerService.getOwnerById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OwnerDto createOwner(@Valid @RequestBody OwnerDto ownerDto) {
        OwnerModel ownerModel = mappingUtils.mapToOwnerModelFromDto(ownerDto);
        return mappingUtils.mapToOwnerDto(ownerService.createOwner(ownerModel));
    }

    @PutMapping("/{id}")
    public OwnerDto updateOwner(@PathVariable Long id, @Valid @RequestBody OwnerDto ownerDto) {
        OwnerModel ownerModel = mappingUtils.mapToOwnerModelFromDto(ownerDto);
        return mappingUtils.mapToOwnerDto(ownerService.updateOwner(id, ownerModel));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOwner(@PathVariable Long id) {
        ownerService.deleteOwner(id);
    }
}
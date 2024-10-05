package test.gai.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import test.gai.model.Owner;
import test.gai.service.OwnerService;

import java.util.List;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @GetMapping
    public List<Owner> getAllOwners() {
        return ownerService.getAllOwners();
    }

    @GetMapping("/{id}")
    public Owner getOwnerById(@PathVariable Long id) {
        return ownerService.getOwnerById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Owner createOwner(@Valid @RequestBody Owner owner) {
        return ownerService.createOwner(owner);
    }

    @PutMapping("/{id}")
    public Owner updateOwner(@PathVariable Long id, @Valid @RequestBody Owner owner) {
        return ownerService.updateOwner(id, owner);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOwner(@PathVariable Long id) {
        ownerService.deleteOwner(id);
    }
}
package ru.example.edu.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.edu.dto.PersonWithInvitesDTO;
import ru.example.edu.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("api/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    @GetMapping
    public List<PersonWithInvitesDTO> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonWithInvitesDTO> getPersonById(@PathVariable Long id) {
        return ResponseEntity.ok(personService.getPersonByUd(id));
    }

    @PostMapping("/register")
    public ResponseEntity<PersonWithInvitesDTO> createPerson(@RequestBody PersonWithInvitesDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.createPerson(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonWithInvitesDTO> updatePerson(@PathVariable Long id, @RequestBody PersonWithInvitesDTO dto) {
        return ResponseEntity.ok(personService.updatePerson(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }
}

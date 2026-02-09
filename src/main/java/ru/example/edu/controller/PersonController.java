package ru.example.edu.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.example.edu.dto.PersonRegisterDTO;
import ru.example.edu.dto.PersonShortDTO;
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
        return ResponseEntity.ok(personService.getPersonById(id));
    }

    @GetMapping("/login")
    public ResponseEntity<PersonWithInvitesDTO> loginPerson(Authentication authentication) {
        return ResponseEntity.ok(personService.getPersonByLogin(authentication.getName()));
    }

    @PostMapping("/register")
    public ResponseEntity<PersonShortDTO> createPerson(@RequestBody PersonRegisterDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.createPerson(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonWithInvitesDTO> updatePerson(@PathVariable Long id, @RequestBody PersonShortDTO dto) {
        return ResponseEntity.ok(personService.updatePerson(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/login/{login}")
    public ResponseEntity<String> getByLogin(@PathVariable String login) {
        PersonWithInvitesDTO person = personService.getPersonByLogin(login);
        return ResponseEntity.ok("User " + person.getLogin() + " is registered");
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<PersonWithInvitesDTO>> getAllPersonPaginated(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(personService.getAllPersonPaginated(pageable));
    }

    @GetMapping("/name")
    public ResponseEntity<List<PersonShortDTO>> getPersonWithNameLike(@RequestParam String likeName) {
        return ResponseEntity.ok(personService.getPersonWithNameLike(likeName));
    }
}

package ru.sicampus.bootcamp2026.api;

import jakarta.validation.Valid;
import java.util.List;
import ru.sicampus.bootcamp2026.api.dto.AuthenticationResponse;
import ru.sicampus.bootcamp2026.api.dto.PersonRegisterRequest;
import ru.sicampus.bootcamp2026.api.dto.PersonResponse;
import ru.sicampus.bootcamp2026.api.dto.PersonUpdateRequest;
import ru.sicampus.bootcamp2026.domain.Person;
import ru.sicampus.bootcamp2026.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/person")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // Public: POST /api/person/register
    @PostMapping("/register")
    public ResponseEntity<PersonResponse> register(@Valid @RequestBody PersonRegisterRequest request) {
        Person created = personService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(created));
    }

    // Public: GET /api/person/email/{email}
    @GetMapping("/email/{email}")
    public PersonResponse getByEmail(@PathVariable String email) {
        return toResponse(personService.getByEmail(email));
    }

    // Private: GET /api/person/login (HTTP Basic)
    @GetMapping("/login")
    public AuthenticationResponse login(Authentication authentication) {
        Person principal = (Person) authentication.getPrincipal();
        return new AuthenticationResponse(
            authentication.getName(),
            authentication.getAuthorities().stream().map(a -> a.getAuthority()).toList(),
            toResponse(principal)
        );
    }

    // Private: other /api/person/** endpoints (configured in WebSecurityConfig)
    @GetMapping
    public List<PersonResponse> list() {
        return personService.list().stream().map(PersonController::toResponse).toList();
    }

    @GetMapping("/{id}")
    public PersonResponse get(@PathVariable Long id) {
        return toResponse(personService.get(id));
    }

    @PutMapping("/{id}")
    public PersonResponse update(@PathVariable Long id, @Valid @RequestBody PersonUpdateRequest request) {
        return toResponse(personService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private static PersonResponse toResponse(Person person) {
        return new PersonResponse(
            person.getId(),
            person.getEmail(),
            person.getFullName(),
            person.getDepartment(),
            person.getPosition(),
            person.getCreatedAt(),
            person.getAuthorities().stream().map(a -> a.getAuthority()).toList()
        );
    }
}


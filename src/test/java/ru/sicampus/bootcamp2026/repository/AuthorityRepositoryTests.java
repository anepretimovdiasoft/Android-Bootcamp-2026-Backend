package ru.sicampus.bootcamp2026.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.sicampus.bootcamp2026.entity.Authority;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Transactional
@ActiveProfiles("tests")
public class AuthorityRepositoryTests {
    @Autowired
    private AuthorityRepository authorityRepository;

    @Test
    void getAuthorityById() {
        Authority authority = authorityRepository.findById(1L).orElse(null);
        assertNotNull(authority);
        assertEquals("EMPLOYEE", authority.getAuthority());
    }

    @Test
    void saveAuthority() {
        Authority authority = new Authority();
        authority.setAuthority("TEST_AUTHORITY");
        Authority saved = authorityRepository.save(authority);

        assertEquals("TEST_AUTHORITY", authorityRepository.findById(saved.getId()).orElse(null).getAuthority());
    }
}

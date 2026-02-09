package com.teto.planner.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.teto.planner.entity.UserEntity;
import com.teto.planner.repository.UserRepository;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServicePersistenceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void updateMePersistsChangesEvenIfUserIsDetached() {
        UUID id = UUID.randomUUID();
        UserEntity u = new UserEntity();
        u.setId(id);
        u.setLogin("test_detached_" + id + "@example.com");
        u.setName("Old");
        u.setPasswordHash("hash"); // not used in this test, but required by schema
        u.setTelegramNick("@old");
        u.setBio("old bio");
        userRepository.saveAndFlush(u);

        UserEntity detached = new UserEntity();
        detached.setId(id);

        userService.updateMe(detached, "New", "@new", "new bio");

        UserEntity reloaded = userRepository.findById(id).orElseThrow();
        assertEquals("New", reloaded.getName());
        assertEquals("@new", reloaded.getTelegramNick());
        assertEquals("new bio", reloaded.getBio());
        assertNotNull(reloaded.getUpdatedAt());
    }

    @Test
    void updateAvatarPersistsChangesEvenIfUserIsDetached() {
        UUID id = UUID.randomUUID();
        UserEntity u = new UserEntity();
        u.setId(id);
        u.setLogin("test_avatar_" + id + "@example.com");
        u.setName("Name");
        u.setPasswordHash("hash");
        userRepository.saveAndFlush(u);

        UserEntity detached = new UserEntity();
        detached.setId(id);

        userService.updateAvatar(detached, new byte[]{1, 2, 3}, "image/png");

        UserEntity reloaded = userRepository.findById(id).orElseThrow();
        assertEquals("image/png", reloaded.getAvatarContentType());
        assertEquals(3, reloaded.getAvatarBytes().length);
    }
}


package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.request.RegisterRequest;

public interface UserService {

    // create
    void createUser(RegisterRequest request);
}

package com.nikshet.industrialbackend.exception

import org.springframework.http.HttpStatus

class InvalidLoginOrPasswordException : ServiceException("Неверный логин или пароль", HttpStatus.UNAUTHORIZED)
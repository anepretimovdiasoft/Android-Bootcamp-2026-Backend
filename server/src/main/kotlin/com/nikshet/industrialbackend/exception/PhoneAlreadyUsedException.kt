package com.nikshet.industrialbackend.exception

import org.springframework.http.HttpStatus


class PhoneAlreadyUsedException :
    ServiceException("Номер телефона уже используется другим пользователем", HttpStatus.CONFLICT) {
}
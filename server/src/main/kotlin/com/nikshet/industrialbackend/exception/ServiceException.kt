package com.nikshet.industrialbackend.exception

import org.springframework.http.HttpStatus


open class ServiceException(message: String, val statusCode: HttpStatus) : Exception(message) {
}
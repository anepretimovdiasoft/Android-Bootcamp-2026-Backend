package com.nikshet.industrialbackend.dto.response

import com.nikshet.industrialbackend.entity.UserEntity
import java.util.UUID

data class UserInfoResponse(
    val id: UUID,
    val phoneNumber: String,
    val fullName: String,
    val department: String,
) {
    companion object {
        fun from(entity: UserEntity): UserInfoResponse = UserInfoResponse(
            id = entity.id,
            phoneNumber = entity.phoneNumber,
            fullName = entity.fullName,
            department = entity.department,
        )
    }
}
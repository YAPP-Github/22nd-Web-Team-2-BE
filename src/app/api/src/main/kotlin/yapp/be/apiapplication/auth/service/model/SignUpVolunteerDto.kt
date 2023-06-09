package yapp.be.apiapplication.auth.service.model

import yapp.be.model.vo.Email

class SignUpUserRequestDto(
    val nickname: String,
    val email: Email,
    val phoneNumber: String,
)

data class SignUpUserWithEssentialInfoResponseDto(
    val userId: Long,
)
data class CheckUserNicknameExistResponseDto(
    val isExist: Boolean
)

package yapp.be.domain.port.inbound

import yapp.be.domain.model.ShelterUser
import yapp.be.model.Email

interface ShelterSignUpUseCase {
    fun signUpWithEssentialInfo(
        shelterId: Long,
        email: Email,
        password: String,
        phoneNumber: String,
    ): ShelterUser
}
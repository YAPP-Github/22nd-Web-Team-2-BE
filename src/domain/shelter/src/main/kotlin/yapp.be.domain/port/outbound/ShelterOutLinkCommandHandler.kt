package yapp.be.domain.port.outbound

import yapp.be.domain.model.ShelterOutLink

interface ShelterOutLinkCommandHandler {
    fun upsertAll(
        shelterId: Long,
        outLinks: List<ShelterOutLink>
    ): List<ShelterOutLink>
}

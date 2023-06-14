package yapp.be.domain.model

import yapp.be.enum.OutLinkType

data class ShelterOutLink(
    val id: Long,
    val url: String,
    val type: OutLinkType,
    val shelterId: Long,
)
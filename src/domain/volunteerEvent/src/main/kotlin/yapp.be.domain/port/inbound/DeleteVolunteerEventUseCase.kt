package yapp.be.domain.port.inbound

interface DeleteVolunteerEventUseCase {
    fun deleteByIdAndShelterId(
        id: Long,
        shelterId: Long
    )
}

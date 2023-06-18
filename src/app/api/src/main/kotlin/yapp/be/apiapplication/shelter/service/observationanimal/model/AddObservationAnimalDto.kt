package yapp.be.apiapplication.shelter.service.observationanimal.model

import yapp.be.enum.Gender

data class AddObservationAnimalRequestDto(
    val images: List<String>,
    val name: String,
    val age: Int,
    val gender: Gender,
    val breed: String,
    val specialNote: String
)

data class AddObservationAnimalResponseDto(
    val observationAnimalId: Long
)

data class EditObservationAnimalRequestDto(
    val images: List<String>,
    val name: String,
    val age: Int,
    val gender: Gender,
    val breed: String,
    val specialNote: String
)
data class EditObservationAnimalResponseDto(
    val observationAnimalId: Long
)
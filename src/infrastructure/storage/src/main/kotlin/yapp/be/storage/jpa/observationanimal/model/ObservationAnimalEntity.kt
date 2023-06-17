package yapp.be.storage.jpa.observationanimal.model

import jakarta.persistence.*
import yapp.be.enum.Gender
import yapp.be.storage.jpa.common.model.BaseTimeEntity

@Entity
@Table(
    name = "observation_animal",
    indexes = [
        Index(name = "IDX_SHELTER_ID", columnList = "shelter_id")
    ]
)
class ObservationAnimalEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "name")
    val name: String,

    @Column(name = "profile_image_url")
    val profileImageUrl: String,

    @Column(name = "age")
    val age: Int,

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    val gender: Gender,

    @Column(name = "special_note")
    val specialNote: String,

    @Column(name = "shelter_id")
    val shelterId: Long
) : BaseTimeEntity()

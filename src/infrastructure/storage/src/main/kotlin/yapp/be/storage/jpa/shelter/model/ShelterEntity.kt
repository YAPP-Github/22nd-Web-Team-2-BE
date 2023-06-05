package yapp.be.storage.jpa.shelter.model

import jakarta.persistence.*
import yapp.be.storage.jpa.common.model.Address

@Entity
@Table(name = "shelter_entity")
class ShelterEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column
    val name: String,
    @Column
    val description: String,
    @Column
    val address: Address,
    @Column
    val phone: String,
    @Column
    val notice: String,
    @Column
    val parking: Boolean = false,
    @Column
    val account: String,
    @Column
    val bankName: String,
    @Column
    val donationUrl: String,
    @Column
    val image: String,
    @Column
    val email: String,
)

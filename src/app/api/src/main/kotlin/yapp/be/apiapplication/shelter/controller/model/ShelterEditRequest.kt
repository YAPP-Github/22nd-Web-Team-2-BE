package yapp.be.apiapplication.shelter.controller.model

import yapp.be.apiapplication.auth.controller.model.ShelterSignUpAddressInfo
import yapp.be.apiapplication.shelter.service.model.EditShelterProfileImageRequestDto
import yapp.be.apiapplication.shelter.service.model.EditShelterWithAdditionalInfoRequestDto
import yapp.be.apiapplication.shelter.service.model.EditWithEssentialInfoRequestDto
import yapp.be.model.vo.Address
import yapp.be.domain.model.BankAccount
import yapp.be.domain.model.ShelterParkingInfo
import yapp.be.model.enums.volunteerevent.OutLinkType

data class EditShelterProfileImageRequest(
    val url: String
) {
    fun toDto(): EditShelterProfileImageRequestDto {
        return EditShelterProfileImageRequestDto(
            profileImageUrl = url
        )
    }
}

data class EditShelterEssentialInfoRequest(
    val name: String,
    val phoneNumber: String,
    val description: String,
    val address: ShelterSignUpAddressInfo,

) {
    fun toDto(): EditWithEssentialInfoRequestDto {
        return EditWithEssentialInfoRequestDto(
            name = name,
            phoneNumber = phoneNumber,
            description = description,
            address = Address(
                address = this.address.address,
                addressDetail = this.address.addressDetail,
                postalCode = this.address.postalCode,
                latitude = this.address.latitude,
                longitude = this.address.longitude
            )
        )
    }
}

data class EditShelterAdditionalInfoRequest(
    val outLinks: List<EditShelterOutLinkInfo>,
    val parkingInfo: EditShelterParkingInfo?,
    val bankAccount: EditShelterDonationInfo?,
    val notice: String?,
) {
    fun toDto(): EditShelterWithAdditionalInfoRequestDto {
        return EditShelterWithAdditionalInfoRequestDto(
            outLinks = this.outLinks.map {
                Pair(it.outLinkType, it.url)
            },
            bankAccount = bankAccount?.let {
                BankAccount(
                    name = this.bankAccount.bankName,
                    accountNumber = this.bankAccount.accountNumber,
                )
            },
            parkingInfo = parkingInfo?.let {
                ShelterParkingInfo(
                    parkingEnabled = this.parkingInfo.parkingEnabled,
                    parkingNotice = this.parkingInfo.parkingNotice
                )
            },
            notice = this.notice
        )
    }
    data class EditShelterParkingInfo(
        val parkingEnabled: Boolean,
        val parkingNotice: String,
    )
    data class EditShelterDonationInfo(
        val accountNumber: String,
        val bankName: String
    )
    data class EditShelterOutLinkInfo(
        val outLinkType: OutLinkType,
        val url: String
    )
}

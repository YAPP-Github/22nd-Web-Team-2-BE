package yapp.be.storage.jpa.user.model

import jakarta.persistence.*
import yapp.be.enum.OAuthType
import yapp.be.enum.Role
import yapp.be.storage.jpa.common.model.BaseTimeEntity

@Entity
@Table(name = "user")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "email", unique = true)
    val email: String,
    @Column(name = "nickname", unique = true)
    val nickname: String,
    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    val role: Role = Role.VOLUNTEER,
    @Column(name = "o_auth_type")
    @Enumerated(EnumType.STRING)
    val oAuthType: OAuthType,
    @Column(name = "o_auth_access_token")
    val oAuthAccessToken: String,
    @Column(name = "is_deleted")
    val deleted: Boolean = false,
) : BaseTimeEntity()
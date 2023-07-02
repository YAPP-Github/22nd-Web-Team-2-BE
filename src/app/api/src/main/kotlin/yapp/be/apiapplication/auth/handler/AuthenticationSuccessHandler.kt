package yapp.be.apiapplication.auth.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import yapp.be.apiapplication.system.exception.ApiExceptionType
import yapp.be.apiapplication.system.security.JwtTokenProvider
import yapp.be.apiapplication.system.security.SecurityTokenType
import yapp.be.apiapplication.system.security.oauth2.CustomOAuth2User
import yapp.be.apiapplication.system.security.properties.JwtConfigProperties
import yapp.be.domain.port.inbound.*
import yapp.be.exceptions.CustomException
import yapp.be.model.Email
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.time.Duration

@Component
class AuthenticationSuccessHandler(
    @Value("\${oauth.redirect-url}")
    private val REDIRECT_URI: String,
    private val jwtTokenProvider: JwtTokenProvider,
    private val getOAuthNonMemberInfoUseCase: GetOAuthNonMemberInfoUseCase,
    private val saveOAuthNonMemberInfoUseCase: SaveOAuthNonMemberInfoUseCase,
    private val getVolunteerUseCase: GetVolunteerUseCase,
    private val checkVolunteerUseCase: CheckVolunteerUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val jwtConfigProperties: JwtConfigProperties,
) : SimpleUrlAuthenticationSuccessHandler() {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val customOAuth2User = authentication.principal as CustomOAuth2User
        val userEmail = Email(customOAuth2User.customOAuthAttributes.email)
        val isMember = checkVolunteerUseCase.isExistByEmail(userEmail)

        if (isMember) {
            val user = getVolunteerUseCase.getByEmail(userEmail)
            val authToken = jwtTokenProvider.generateToken(
                id = user.id,
                email = user.email,
                role = user.role,
                securityTokenType = SecurityTokenType.AUTH
            )

            val accessToken = jwtTokenProvider.generateToken(
                id = user.id,
                email = user.email,
                role = user.role,
                securityTokenType = SecurityTokenType.ACCESS
            )
            val refreshToken = jwtTokenProvider.generateToken(
                id = user.id,
                email = user.email,
                role = user.role,
                securityTokenType = SecurityTokenType.REFRESH
            )

            saveTokenUseCase.saveTokensWithAuthToken(
                authToken = authToken,
                accessToken = accessToken,
                refreshToken = refreshToken,
                authTokenExpire = Duration.ofMillis(jwtConfigProperties.auth.expire)
            )

            val param = "authToken=" + URLEncoder.encode(authToken, StandardCharsets.UTF_8)
            redirectStrategy.sendRedirect(request, response, "$REDIRECT_URI?$param")
        } else {
            val oAuthNonMemberUserInfo = getOAuthNonMemberInfoUseCase.get(email = userEmail)
                ?: throw CustomException(ApiExceptionType.UNAUTHORIZED_EXCEPTION, "회원가입 유효시간이 만료되었거나, 올바른 접근이 아닙니다.")

            val param = "email=" + URLEncoder.encode(userEmail.value, StandardCharsets.UTF_8) +
                "&isMember=" + false
            redirectStrategy.sendRedirect(request, response, "$REDIRECT_URI?$param")
        }
    }
}

package com.lucky.scoreboard.controllers

import com.lucky.scoreboard.models.RoleEnum
import com.lucky.scoreboard.models.Role
import com.lucky.scoreboard.models.User
import com.lucky.scoreboard.repositories.RoleRepository
import com.lucky.scoreboard.repositories.UserRepository
import com.lucky.scoreboard.requests.LoginRequest
import com.lucky.scoreboard.requests.SignupRequest
import com.lucky.scoreboard.response.JwtResponse
import com.lucky.scoreboard.response.MessageResponse
import com.lucky.scoreboard.security.services.UserDetailsImpl
import com.lucky.scoreboard.security.tokens.JwtUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors
import javax.validation.Valid

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
class SignController {
    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Autowired
    lateinit var encoder: PasswordEncoder

    @Autowired
    lateinit var jwtUtils: JwtUtils

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: @Valid LoginRequest): ResponseEntity<*> {
        return ResponseEntity.ok<Any>(
            JwtResponse(
                generateAuthToken(loginRequest.username, loginRequest.password)
            )
        )
    }

    @PostMapping("/signup")
    fun signup(@RequestBody signUpRequest: @Valid SignupRequest): ResponseEntity<*> {
        if (userRepository.existsByUsername(signUpRequest.username)) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(MessageResponse("Error: username is already used"))
        }

        val user = User(
            signUpRequest.username,
            encoder.encode(signUpRequest.password),
            signUpRequest.score
        )

        if (roleRepository.count() == 0L) {
            roleRepository.save(Role(RoleEnum.ROLE_USER))
        }

        user.roles = hashSetOf(roleRepository.findByName(RoleEnum.ROLE_USER).get())

        userRepository.save(user)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(JwtResponse(generateAuthToken(signUpRequest.username, signUpRequest.password)))
    }

    private fun generateAuthToken(username: String, password: String): String {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(username, password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        return jwtUtils.generateJwtToken(authentication)
    }
}
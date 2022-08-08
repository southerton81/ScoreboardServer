package com.lucky.scoreboard.controllers

import com.lucky.scoreboard.repositories.UserRepository
import com.lucky.scoreboard.response.MessageResponse
import com.lucky.scoreboard.response.UserScore
import com.lucky.scoreboard.security.services.UserDetailsImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/users")
class UserController {
    @Autowired
    lateinit var userRepository: UserRepository

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    fun getUser(authentication: Authentication): ResponseEntity<*> {
        val userDetails = authentication.principal as UserDetailsImpl
        val user = userRepository.getReferenceById(userDetails.id)
        return ResponseEntity<UserScore>(UserScore(user.username, user.score), HttpStatus.OK)
    }
}
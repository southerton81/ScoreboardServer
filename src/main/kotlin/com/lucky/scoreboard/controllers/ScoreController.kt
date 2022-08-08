package com.lucky.scoreboard.controllers

import com.lucky.scoreboard.repositories.UserRepository
import com.lucky.scoreboard.requests.ScoreRequest
import com.lucky.scoreboard.response.MessageResponse
import com.lucky.scoreboard.response.UserScore
import com.lucky.scoreboard.security.services.UserDetailsImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/scores")
class ScoreController {
    @Autowired
    lateinit var userRepository: UserRepository

    @GetMapping("/")
    fun getScores(): ResponseEntity<List<UserScore>> {
        val userScores = userRepository.findAll(Sort.by(Sort.Direction.DESC, "score"))
            .map {
                UserScore(it.username, it.score)
            }
        return ResponseEntity<List<UserScore>>(userScores, HttpStatus.OK)
    }

    @PostMapping("/score")
    @PreAuthorize("hasRole('USER')")
    fun postScore(authentication: Authentication, @RequestBody scoreRequest: @Valid ScoreRequest): ResponseEntity<*> {
        val userDetails = authentication.principal as UserDetailsImpl
        val user = userRepository.getReferenceById(userDetails.id)
        user.score = scoreRequest.score
        userRepository.save(user)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(MessageResponse("Success: score posted"))
    }
}
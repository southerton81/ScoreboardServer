package com.lucky.scoreboard.requests

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class SignupRequest (
    @NotBlank
    @Size(min = 3, max = 20)
    var username: String,

    @NotBlank
    @Size(min = 7, max = 128)
    var password: String,

    var score: Long
)
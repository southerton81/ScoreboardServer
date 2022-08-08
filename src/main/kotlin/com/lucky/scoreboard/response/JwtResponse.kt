package com.lucky.scoreboard.response


class JwtResponse(
    var accessToken: String
) {
    var tokenType = "Bearer"
}
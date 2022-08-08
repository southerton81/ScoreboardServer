package com.lucky.scoreboard

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class ScoreboardApplication

fun main(args: Array<String>) {
	runApplication<ScoreboardApplication>(*args)
}


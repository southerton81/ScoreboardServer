package com.lucky.scoreboard.repositories

import com.lucky.scoreboard.models.RoleEnum
import com.lucky.scoreboard.models.Role

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
    fun findByName(name: RoleEnum): Optional<Role>
}
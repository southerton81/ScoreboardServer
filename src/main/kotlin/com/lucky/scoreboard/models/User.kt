package com.lucky.scoreboard.models

import javax.persistence.*

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(
    name = "users",
    uniqueConstraints = [UniqueConstraint(columnNames = arrayOf("username"))]
)
class User(
    @NotBlank
    @Size(max = 20) var username: String,
    @NotBlank
    @Size(max = 120) var password: String,
    var score: Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: Set<Role> = hashSetOf()
}
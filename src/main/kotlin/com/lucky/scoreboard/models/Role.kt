package com.lucky.scoreboard.models

import javax.persistence.*


@Entity
@Table(name = "roles")
class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var name: RoleEnum? = null

    constructor() {}

    constructor(name: RoleEnum?) {
        this.name = name
    }
}
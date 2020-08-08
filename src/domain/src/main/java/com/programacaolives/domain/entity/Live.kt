package com.programacaolives.domain.entity

import java.util.*

data class Live(
    val name: String,
    val imageName: String,
    val date: Date,
    val link: String,
    val noSetTime: Boolean
)
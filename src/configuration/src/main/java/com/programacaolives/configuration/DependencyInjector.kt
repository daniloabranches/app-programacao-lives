package com.programacaolives.configuration

import com.programacaolives.domain.usecase.*

interface DependencyInjector {
    fun baseUrl(): String
    fun getScheduleUseCase(): GetScheduleUseCase
}
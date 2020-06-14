package com.programacaolives.configuration

import com.programacaolives.data.configuration.RetrofitModule
import com.programacaolives.data.repository.ScheduleDataRepository
import com.programacaolives.domain.core.DateService
import com.programacaolives.domain.usecase.GetScheduleUseCase

class AppDependencyInjector(
    private val dateService: DateService,
    private val retrofitBaseUrl: String
) : DependencyInjector {
    private val retrofit = RetrofitModule.get(baseUrl())
    private val scheduleDataRepository = ScheduleDataRepository(retrofit)

    override fun baseUrl() = retrofitBaseUrl

    override fun getScheduleUseCase(): GetScheduleUseCase =
        GetScheduleUseCase(dateService, scheduleDataRepository)
}
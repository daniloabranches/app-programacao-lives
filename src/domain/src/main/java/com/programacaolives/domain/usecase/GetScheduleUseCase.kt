package com.programacaolives.domain.usecase

import com.programacaolives.domain.core.DateService
import com.programacaolives.domain.entity.Live
import com.programacaolives.domain.repository.ScheduleRepository
import io.reactivex.rxjava3.core.Single
import java.util.*

class GetScheduleUseCase(
    private val dateService: DateService,
    private val scheduleRepository: ScheduleRepository
) {
    operator fun invoke(): Single<List<Live>> {
        val calendar = Calendar.getInstance().apply {
            time = dateService.getDateNow()
            add(Calendar.HOUR_OF_DAY, -4)
        }

        return scheduleRepository.getSchedule().map { removeOldLives(it, calendar.time) }
    }

    private fun removeOldLives(lives: List<Live>, date: Date) = lives.filter { it.date > date }
}
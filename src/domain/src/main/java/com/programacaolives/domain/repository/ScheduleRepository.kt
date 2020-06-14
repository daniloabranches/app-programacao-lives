package com.programacaolives.domain.repository

import com.programacaolives.domain.entity.Live
import io.reactivex.rxjava3.core.Single

interface ScheduleRepository {
    fun getSchedule(): Single<List<Live>>
}
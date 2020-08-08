package com.programacaolives.data.repository

import com.programacaolives.data.entity.Live
import com.programacaolives.data.remote.ScheduleService
import com.programacaolives.domain.repository.ScheduleRepository
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit

class ScheduleDataRepository(private val retrofit: Retrofit) : ScheduleRepository {
    override fun getSchedule(): Single<List<com.programacaolives.domain.entity.Live>> {
        val scheduleService = retrofit.create(ScheduleService::class.java)
        return scheduleService.getSchedule().map(::map)
    }

    private fun map(lives: List<Live>): List<com.programacaolives.domain.entity.Live> {
        return lives.map {
            com.programacaolives.domain.entity.Live(
                it.name,
                it.image_name,
                it.date,
                it.link,
                it.no_set_time
            )
        }
    }
}
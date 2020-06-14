package com.programacaolives.data.remote

import com.programacaolives.data.entity.Live
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface ScheduleService {
    @GET("build_schedule_lives.json")
    fun getSchedule(): Single<List<Live>>
}
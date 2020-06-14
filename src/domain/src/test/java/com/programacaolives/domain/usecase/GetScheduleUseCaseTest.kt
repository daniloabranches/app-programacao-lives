package com.programacaolives.domain.usecase

import com.programacaolives.domain.core.DateService
import com.programacaolives.domain.entity.Live
import com.programacaolives.domain.repository.ScheduleRepository
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.text.SimpleDateFormat
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class GetScheduleUseCaseTest {

    private val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())

    @Mock
    private lateinit var dateService: DateService

    @Mock
    private lateinit var scheduleRepository: ScheduleRepository

    @Test
    fun should_GetLives_When_ThereAreNoLives() {
        Mockito.`when`(dateService.getDateNow())
            .thenReturn(simpleDateFormat.parse("17/05/2020 10:30:00"))

        val repositoryLives = listOf<Live>()

        Mockito.`when`(scheduleRepository.getSchedule()).thenReturn(Single.just(repositoryLives))

        val getScheduleUseCase = GetScheduleUseCase(dateService, scheduleRepository)

        val expectedLives = listOf<Live>()
        getScheduleUseCase().test().assertResult(expectedLives)
    }

    @Test
    fun should_GetLives_When_ThereAreNoOldLives() {
        Mockito.`when`(dateService.getDateNow())
            .thenReturn(simpleDateFormat.parse("25/10/2020 10:30:00"))

        val repositoryLives = listOf(
            Live("Live 1", "", simpleDateFormat.parse("25/10/2020 18:30:00"), "", false),
            Live("Live 2", "", simpleDateFormat.parse("26/10/2020 22:30:00"), "", false),
            Live("Live 3", "", simpleDateFormat.parse("27/10/2020 19:00:00"), "", false)
        )

        Mockito.`when`(scheduleRepository.getSchedule()).thenReturn(Single.just(repositoryLives))

        val getScheduleUseCase = GetScheduleUseCase(dateService, scheduleRepository)

        val expectedLives = listOf(
            Live("Live 1", "", simpleDateFormat.parse("25/10/2020 18:30:00"), "", false),
            Live("Live 2", "", simpleDateFormat.parse("26/10/2020 22:30:00"), "", false),
            Live("Live 3", "", simpleDateFormat.parse("27/10/2020 19:00:00"), "", false)
        )

        getScheduleUseCase().test().assertResult(expectedLives)
    }

    @Test
    fun should_GetLives_When_ThereAreOldLives() {
        Mockito.`when`(dateService.getDateNow())
            .thenReturn(simpleDateFormat.parse("25/10/2020 18:00:00"))

        val repositoryLives = listOf(
            Live("Live 1", "", simpleDateFormat.parse("25/10/2020 11:00:00"), "", false),
            Live("Live 2", "", simpleDateFormat.parse("25/10/2020 22:30:00"), "", false),
            Live("Live 3", "", simpleDateFormat.parse("25/10/2020 14:00:00"), "", false),
            Live("Live 4", "", simpleDateFormat.parse("05/11/2020 20:30:00"), "", false),
            Live("Live 5", "", simpleDateFormat.parse("24/10/2020 19:00:00"), "", false)
        )

        Mockito.`when`(scheduleRepository.getSchedule()).thenReturn(Single.just(repositoryLives))

        val getScheduleUseCase = GetScheduleUseCase(dateService, scheduleRepository)

        val expectedLives = listOf(
            Live(
                "Live 2",
                "",
                simpleDateFormat.parse("25/10/2020 22:30:00"),
                "",
                false
            ),
            Live("Live 4", "", simpleDateFormat.parse("05/11/2020 20:30:00"), "", false)
        )

        getScheduleUseCase().test().assertResult(expectedLives)
    }
}
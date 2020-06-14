package com.programacaolives.utils.date

import com.programacaolives.domain.core.DateService
import java.util.*

class DateServiceImpl : DateService {
    override fun getDateNow() = Date()
}
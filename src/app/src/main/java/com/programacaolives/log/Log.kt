package com.programacaolives.log

import io.sentry.core.Sentry
import io.sentry.core.SentryLevel

class Log {
    companion object {
        fun exception(exception: Throwable) {
            Sentry.captureException(exception)
        }

        fun log(message: String) {
            Sentry.captureMessage(message, SentryLevel.LOG)
        }
    }
}
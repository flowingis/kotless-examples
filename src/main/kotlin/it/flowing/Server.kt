package it.flowing

import io.kotless.dsl.ktor.Kotless
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class Info(_uptime: OffsetDateTime) {
    val time = _uptime.format(DateTimeFormatter.ISO_DATE_TIME)
}

val info = Info(OffsetDateTime.now())

class Server : Kotless() {
    override fun prepare(app: Application) {
        app.install(CORS) {
            method(HttpMethod.Options)
            method(HttpMethod.Put)
            method(HttpMethod.Delete)
            method(HttpMethod.Patch)
            header(HttpHeaders.Authorization)
            header("MyCustomHeader")
            allowCredentials = true
            anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
        }

        app.install(ContentNegotiation) {
            gson {
            }
        }

        app.routing {
            get("/") {
                call.respond(info)
            }
        }
    }
}
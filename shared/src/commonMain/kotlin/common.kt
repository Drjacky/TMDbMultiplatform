package app.web.drjackycv.beermultiplatform.shared

expect fun platformName(): String

fun HelloWorldMessage(): String {
    return "Hello world from ${platformName()}"
}
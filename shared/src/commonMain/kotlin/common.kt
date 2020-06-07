package app.web.drjackycv.omdbmultiplatform.shared

expect fun platformName(): String

fun HelloWorldMessage(): String {
    return "Hello world from ${platformName()}"
}
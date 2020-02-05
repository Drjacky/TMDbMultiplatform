package app.web.drjackycv.flickrmultiplatform.shared

expect fun platformName(): String

fun HelloWorldMessage(): String {
    return "Hello world from ${platformName()}"
}
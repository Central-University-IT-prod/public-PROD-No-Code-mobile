package com.ari.prodvizhenie.auth.domain.model

data class NetworkError(
    val error: ApiError,
    val t: Throwable? = null
)

enum class ApiError(val message: String) {
    NetworkError("Проверьте подключение к интернету"),
    UnknownError("Неизвестная ошибка"),
    UnknownResponse("Неправильный логин или пароль")
}
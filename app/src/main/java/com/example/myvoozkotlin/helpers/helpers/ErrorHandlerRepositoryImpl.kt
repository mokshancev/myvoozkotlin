package com.example.myvoozkotlin.helpers.helpers

open class ErrorHandlerRepositoryImpl: ErrorHandlerRepository {
    override fun handleErrorMessage(errorMessage: String): String {
        return when {
            errorMessage.contains("Unable to resolve host") -> "Отсутствует подключение к интернету. Попробуйте повторить позже."
            errorMessage.contains("Forbidden") -> "Произошла ошибка. Не удалось загрузить необходимые данные."
            errorMessage.contains("Bad Request") -> "Не удалось отправить запрос. Попробуйте повторить позже."
            else -> "При отправке запроса произошла ошибка. Попробуйте повторить позже."
        }
    }
}
package com.example.myvoozkotlin.helpers

class Event<out T>(val status: Status, val data: T?, val error: String?) {
    companion object {
        fun <T> loading(): Event<T> {
            return Event(Status.LOADING, null, null)
        }

        fun <T> success(data: T): Event<T> {
            return Event(Status.SUCCESS, data, null)
        }

        fun <T> error(cause: String?): Event<T> {
            return Event(Status.ERROR, null, cause)
        }
    }
}
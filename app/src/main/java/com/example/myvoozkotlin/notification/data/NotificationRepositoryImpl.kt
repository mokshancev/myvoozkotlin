package com.example.myvoozkotlin.notification.data

import com.example.homelibrary.model.Notification
import com.example.myvoozkotlin.notification.data.api.NotificationApi
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.notification.domain.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationApi: NotificationApi
) : NotificationRepository {

    override fun loadNotificationUser(
        accessToken: String,
        idUser: Int,
        type: Int
    ): Flow<Event<List<Notification>>> =
        flow<Event<List<Notification>>> {
            emit(Event.loading())
            val apiResponse = notificationApi.loadNotificationUser(accessToken, idUser, type)

            if (apiResponse.isSuccessful && apiResponse.body() != null)
                emit(Event.success(apiResponse.body()!!))
            else{
                emit(Event.error("lol"))
            }
        }.catch { e ->
            emit(Event.error("lol2"))
            e.printStackTrace()
        }

    override fun loadUserWithUniversityNotificationUser(
        accessToken: String,
        idUser: Int,
        type: Int,
        idUniversity: Int
    ): Flow<Event<List<Notification>>> =
        flow<Event<List<Notification>>> {
            emit(Event.loading())
            val apiResponse = notificationApi.loadUserWithUniversityNotificationUser(accessToken, idUser, type, idUniversity)

            if (apiResponse.isSuccessful && apiResponse.body() != null)
                emit(Event.success(apiResponse.body()!!))
            else{
                emit(Event.error("lol"))
            }
        }.catch { e ->
            emit(Event.error("lol2"))
            e.printStackTrace()
        }

    override fun loadUniversityNotification(idUniversity: Int): Flow<Event<List<Notification>>> =
        flow<Event<List<Notification>>> {
            emit(Event.loading())
            val apiResponse = notificationApi.loadUniversityNotification(idUniversity)

            if (apiResponse.isSuccessful && apiResponse.body() != null)
                emit(Event.success(apiResponse.body()!!))
            else{
                emit(Event.error("lol"))
            }
        }.catch { e ->
            emit(Event.error("lol2"))
            e.printStackTrace()
        }
}
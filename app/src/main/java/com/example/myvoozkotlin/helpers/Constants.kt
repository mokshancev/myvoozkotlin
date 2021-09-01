package com.example.myvoozkotlin.helpers

object Constants {
    const val BASE_URL = "https://myvooz.ru/"

    const val APP_PREFERENCES_AUTH_STATE = "app_preferences_auth_state"
    const val APP_PREFERENCES_VK_SOCIAL_LINK = "https://vk.com/myvoooz"
    const val APP_PREFERENCES_VK_PRIVACY_POLICY = "https://vk.com/@myvoooz-privacy"
    const val APP_PREFERENCES_VK_FEEDBACK = "https://vk.com/mail?act=show&peer=-187303171"

    val APP_PREFERENCES_USER_GROUP_ID = "app_preferences_user_group_id"
    val APP_PREFERENCES_USER_GROUP_NAME = "app_preferences_user_group_name"

    val APP_PREFERENCES_USER_UNIVERSITY_NAME = "app_preferences_user_university_name"
    val APP_PREFERENCES_USER_UNIVERSITY_ID = "app_preferences_user_university_id"

    //for notification channel
    const val NIGHT_NOTIFICATION_CHANNEL: String = "night_notification_channel"
    const val NIGHT_NOTIFICATION_CHANNEL_NAME = "Ночные уведомления"
    const val NORMAL_NOTIFICATION_CHANNEL = "normal_notification_channel"
    const val NORMAL_NOTIFICATION_CHANNEL_NAME = "Обычные уведомления"

    const val BROADCAST_ACTION_MESSAGE = "BROADCAST_ACTION_MESSAGE"

    //is night off/on
    const val _NIGHT_PUSH_NOTIFICATION_STATE: String = "night_push"

    //notification commands
    const val LEAVE_GROUP_OF_USER_NOTIFICATION_COMMAND: String = "LEAVE_GROUP_OF_USER"
    const val MAKE_THE_HEAD_USER_NOTIFICATION_COMMAND: String = "CHANGE_USER_RANK"
}
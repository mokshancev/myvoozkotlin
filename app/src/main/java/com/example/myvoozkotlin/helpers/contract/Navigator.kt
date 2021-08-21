package com.example.myvoozkotlin.helpers.contract

import android.os.Parcelable
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner

typealias ResultListener<T> = (T) -> Unit

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {
    fun showAboutScreen()
    fun showNoteScreen()
    fun showHomeScreen()
    fun showSplashScreen()
    fun showSelectGroupScreen()
    fun showCreateGroupOfUserScreen()
    fun showInviteGroupOfUserScreen()
    fun showUserScreen()
    fun showGroupOfUserScreen()
    fun showNotificationScreen()

    fun showDialog(dialogFragment: DialogFragment)

    fun goBack()

    fun goToMenu()

    fun <T : Parcelable> publishResult(result: T)

    fun <T : Parcelable> listenResult(clazz: Class<T>, owner: LifecycleOwner, listener: ResultListener<T>)

}
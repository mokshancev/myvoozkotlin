package com.example.myvoozkotlin.helpers.navigation

import android.os.Parcelable
import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.myvoozkotlin.note.model.Note

typealias ResultListener<T> = (T) -> Unit

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {
    fun showAboutScreen()
    fun showAboutNewScreen()
    fun showNoteListScreen()
    fun showNoteScreen(note: Note)
    fun showHomeScreen()
    fun showAddNoteScreen()
    fun showSplashScreen()
    fun showSelectGroupScreen(@IdRes idRes: Int, isFirst: Boolean, isBackStack: Boolean)
    fun showCreateGroupOfUserScreen()
    fun showInviteGroupOfUserScreen()
    fun showUserScreen()
    fun showGroupOfUserScreen()
    fun showCreateNotificationGOUScreen()
    fun showMainScreen()
    fun showNotificationScreen()

    fun showDialog(dialogFragment: DialogFragment)

    fun goBack()

    fun goToMenu()

    fun <T : Parcelable> publishResult(result: T)

    fun <T : Parcelable> listenResult(clazz: Class<T>, owner: LifecycleOwner, listener: ResultListener<T>)

}
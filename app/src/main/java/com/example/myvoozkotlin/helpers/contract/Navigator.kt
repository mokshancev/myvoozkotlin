package com.example.myvoozkotlin.helpers.contract

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner

typealias ResultListener<T> = (T) -> Unit

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {

    //fun showBoxSelectionScreen(options: Options)

    //fun showOptionsScreen(options: Options)

    fun showAboutScreen(idRes: Int, fragmentManager: FragmentManager)

    fun showNoteScreen(idRes: Int, fragmentManager: FragmentManager)

    fun showHomeScreen(idRes: Int, fragmentManager: FragmentManager)

    //fun showCongratulationsScreen()

    fun goBack()

    fun goToMenu()

    fun <T : Parcelable> publishResult(result: T)

    fun <T : Parcelable> listenResult(clazz: Class<T>, owner: LifecycleOwner, listener: ResultListener<T>)

}
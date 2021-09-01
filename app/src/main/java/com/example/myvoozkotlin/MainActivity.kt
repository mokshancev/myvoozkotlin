package com.example.myvoozkotlin

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.example.myvoozkotlin.about.AboutFragment
import com.example.myvoozkotlin.aboutNew.presentations.AboutNewFragment
import com.example.myvoozkotlin.databinding.ActivityMainBinding
import com.example.myvoozkotlin.fcm.FCMInstance
import com.example.myvoozkotlin.fcm.FCMService
import com.example.myvoozkotlin.groupOfUser.CreateGroupOfUserFragment
import com.example.myvoozkotlin.groupOfUser.CreateNotificationGOUFragment
import com.example.myvoozkotlin.groupOfUser.GroupOfUserFragment
import com.example.myvoozkotlin.groupOfUser.InviteGroupOfUserFragment
import com.example.myvoozkotlin.helpers.navigation.Navigator
import com.example.myvoozkotlin.helpers.navigation.ResultListener
import com.example.myvoozkotlin.helpers.forView.NewDialog
import com.example.myvoozkotlin.home.HomeFragment
import com.example.myvoozkotlin.main.presentation.MainFragment
import com.example.myvoozkotlin.note.AddNoteFragment
import com.example.myvoozkotlin.note.NoteFragment
import com.example.myvoozkotlin.note.NoteListFragment
import com.example.myvoozkotlin.note.model.Note
import com.example.myvoozkotlin.notification.presentation.NotificationFragment
import com.example.myvoozkotlin.searchEmptyAuditory.presentation.SearchEmptyAuditoryFragment
import com.example.myvoozkotlin.selectGroup.SelectGroupFragment
import com.example.myvoozkotlin.splash.SplashFragment
import com.example.myvoozkotlin.user.presentation.UserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Navigator, BottomNavigationView.OnNavigationItemSelectedListener {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var dialog: NewDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureViews()

        startService(Intent(this, FCMInstance::class.java))
        startService(Intent(this, FCMService::class.java))
    }

    private fun configureViews() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.activityMainContainer, SplashFragment(), SplashFragment.javaClass.simpleName)
            .commit()
        hideSystemUI()
    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.activityMainContainer).let { controller ->

        }
        binding.root.setOnApplyWindowInsetsListener { v, insets ->
            println("BOTTOM = ${insets.systemWindowInsetBottom}")
            v.updatePadding(bottom = insets.systemWindowInsetBottom)
            return@setOnApplyWindowInsetsListener insets
        }
    }

    private fun showSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(window, binding.activityMainContainer).show(WindowInsetsCompat.Type.systemBars())
    }

    fun showWait(isShow: Boolean){
        if(dialog == null){
            dialog = NewDialog(R.layout.dialog_fragment_loading)
        }
        dialog?.let {
            it.isCancelable = false
        }
        if(isShow){
            if(!dialog!!.isAdded){
                dialog!!.show(supportFragmentManager, null)
            }
        }
        else{
            if(dialog!!.isAdded){
                dialog!!.dismiss()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true
    }

    override fun showAboutScreen() {
        launchFragment(AboutFragment.newInstance())
    }

    override fun showAboutNewScreen() {
        launchFragmentNoBackStack(AboutNewFragment.newInstance(), R.id.activityMainContainer)
    }

    override fun showNoteListScreen() {
        launchFragment(NoteListFragment.newInstance())
    }

    override fun showNoteScreen(note: Note) {
        launchFragment(NoteFragment.newInstance(note))
    }

    override fun showHomeScreen() {
        launchFragment(HomeFragment.newInstance())
    }

    override fun showAddNoteScreen() {
        launchFragment(AddNoteFragment.newInstance())
    }

    override fun showSearchEmptyAuditoryScreen() {
        launchFragment(SearchEmptyAuditoryFragment.newInstance())
    }

    override fun showSplashScreen() {
        launchFragment(SplashFragment.newInstance(), R.id.activityMainContainer)
    }

    override fun showSelectGroupScreen(@IdRes idRes: Int, isFirst: Boolean, isBackStack: Boolean) {
        if(isBackStack){
            launchFragment(SelectGroupFragment.newInstance(isFirst), idRes)
        }
        else{
            launchFragmentNoBackStack(SelectGroupFragment.newInstance(isFirst), idRes)
        }
    }

    override fun showCreateGroupOfUserScreen() {
        launchFragment(CreateGroupOfUserFragment.newInstance())
    }

    override fun showInviteGroupOfUserScreen() {
        launchFragment(InviteGroupOfUserFragment.newInstance())
    }

    override fun showUserScreen() {
        launchFragment(UserFragment.newInstance())
    }

    override fun showGroupOfUserScreen() {
        launchFragment(GroupOfUserFragment.newInstance(), R.id.rootMainView)
    }

    override fun showCreateNotificationGOUScreen() {
        launchFragment(CreateNotificationGOUFragment.newInstance())
    }

    override fun showMainScreen() {
        launchFragmentNoBackStack(MainFragment.newInstance(), R.id.activityMainContainer)
    }

    override fun showNotificationScreen() {
        launchFragment(NotificationFragment.newInstance())
    }

    override fun showDialog(dialogFragment: DialogFragment) {
        dialogFragment.show(supportFragmentManager,
            dialogFragment::javaClass.javaClass.simpleName)
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun goToMenu() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun <T : Parcelable> publishResult(result: T) {

    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.rootMainView, fragment, fragment.javaClass.simpleName)
            .commit()
    }

    private fun launchFragment(fragment: Fragment, @IdRes idRes: Int) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(idRes, fragment, fragment.javaClass.simpleName)
            .commit()
    }

    private fun launchFragmentNoBackStack(fragment: Fragment, @IdRes idRes: Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(idRes, fragment, fragment.javaClass.simpleName)
            .commit()
    }

    private fun launchFragmentNoBackStack(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.rootMainView, fragment, fragment.javaClass.simpleName)
            .commit()
    }

    override fun <T : Parcelable> listenResult(
        clazz: Class<T>,
        owner: LifecycleOwner,
        listener: ResultListener<T>
    ) {

    }
}
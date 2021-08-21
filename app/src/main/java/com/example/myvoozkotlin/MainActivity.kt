package com.example.myvoozkotlin

import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager.widget.PagerAdapter
import com.example.myvoozkotlin.about.AboutFragment
import com.example.myvoozkotlin.databinding.ActivityMainBinding
import com.example.myvoozkotlin.helpers.contract.Navigator
import com.example.myvoozkotlin.helpers.contract.ResultListener
import com.example.myvoozkotlin.helpers.forView.NewDialog
import com.example.myvoozkotlin.home.HomeFragment
import com.example.myvoozkotlin.note.NoteListFragment
import com.example.myvoozkotlin.splash.SplashFragment
import com.example.myvoozkotlin.user.ChangeFullNameDialogFragment
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
    }

    private fun configureViews() {
        showSplashScreen()
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

    override fun showNoteScreen() {
        launchFragment(NoteListFragment.newInstance())
    }

    override fun showHomeScreen() {
        launchFragment(HomeFragment.newInstance())
    }

    override fun showSplashScreen() {
        launchFragment(SplashFragment.newInstance())
    }

    override fun showSelectGroupScreen() {
        TODO("Not yet implemented")
    }

    override fun showCreateGroupOfUserScreen() {
        TODO("Not yet implemented")
    }

    override fun showInviteGroupOfUserScreen() {
        TODO("Not yet implemented")
    }

    override fun showUserScreen() {
        TODO("Not yet implemented")
    }

    override fun showGroupOfUserScreen() {
        TODO("Not yet implemented")
    }

    override fun showNotificationScreen() {
        TODO("Not yet implemented")
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
            .replace(R.id.rootMainView, fragment, fragment.javaClass.simpleName)
            .addToBackStack(null)
            .commit()
    }

    override fun <T : Parcelable> listenResult(
        clazz: Class<T>,
        owner: LifecycleOwner,
        listener: ResultListener<T>
    ) {

    }
}
package com.example.myvoozkotlin

import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
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
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.activityMainContainer, SplashFragment(), SplashFragment().javaClass.simpleName)
            .addToBackStack(null)
            .commit()
    }

    fun showWait(isShow: Boolean){
        if(dialog == null){
            dialog = NewDialog(R.layout.dialog_fragment_loading)
        }
        dialog?.let {
            it.isCancelable = false
            it.setOnBindViewListener(object : NewDialog.OnBindView {
                override fun onView(view: View?) {

                }
            })
        }
        if(isShow){
            if(!dialog!!.isAdded){
                dialog!!.show(supportFragmentManager, null)
            }
        }
        else{
            dialog!!.dismiss()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        if(item.itemId == R.id.homeFragment)
//            //popBackStack(item, R.id.homeFragment)
//        else if(item.itemId == R.id.profileFragment)
//            //popBackStack(item, R.id.profileFragment)
        return true
    }

    override fun showAboutScreen(idRes: Int, fragmentManager: FragmentManager) {
        launchFragment(idRes, AboutFragment.newInstance(), fragmentManager)
    }

    override fun showNoteScreen(idRes: Int, fragmentManager: FragmentManager) {
        launchFragment(idRes, NoteListFragment.newInstance(), fragmentManager)
    }

    override fun showHomeScreen(idRes: Int, fragmentManager: FragmentManager) {
        launchFragment(idRes, HomeFragment.newInstance(), fragmentManager)
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun goToMenu() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun <T : Parcelable> publishResult(result: T) {
        TODO("Not yet implemented")
    }

    private fun launchFragment(idRes: Int, fragment: Fragment, fragmentManager: FragmentManager) {
        supportFragmentManager
            .beginTransaction()
            .replace(idRes, fragment, fragment.javaClass.simpleName)
            .addToBackStack(null)
            .commit()
    }

    override fun <T : Parcelable> listenResult(
        clazz: Class<T>,
        owner: LifecycleOwner,
        listener: ResultListener<T>
    ) {
        TODO("Not yet implemented")
    }
}
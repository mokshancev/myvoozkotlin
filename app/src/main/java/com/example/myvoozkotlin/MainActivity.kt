package com.example.myvoozkotlin

import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
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
import com.example.myvoozkotlin.home.HomeFragment
import com.example.myvoozkotlin.note.NoteListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Navigator, BottomNavigationView.OnNavigationItemSelectedListener {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureViews()
    }

    private fun configureViews() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.activityMainContainer, MainFragment(), MainFragment().javaClass.simpleName)
            .addToBackStack(null)
            .commit()
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

    inner class ViewPagerAdapter(manager: FragmentManager?) : FragmentStatePagerAdapter(manager!!) {
        private val fragmentList: MutableList<Fragment> = ArrayList()
        var currentFragment: Fragment? = null
            private set

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getItemPosition(`object`: Any): Int {
            return PagerAdapter.POSITION_NONE
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
            if (currentFragment !== `object`) {
                currentFragment = `object` as Fragment

            }
            super.setPrimaryItem(container, position, `object`)
        }

        //adding fragments and title method
        fun addFragment(fragment: Fragment) {
            fragmentList.add(fragment)
        }

        fun replaceFrag(position: Int, fragment: Fragment) {
            fragmentList.removeAt(position)
            fragmentList.add(position, fragment)
        }
    }
}
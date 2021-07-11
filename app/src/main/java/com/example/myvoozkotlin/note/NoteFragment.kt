package com.example.myvoozkotlin.note

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.myvoozkotlin.BaseApp
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.databinding.FragmentNoteBinding
import com.example.myvoozkotlin.helpers.AuthorizationState
import com.example.myvoozkotlin.helpers.hide
import com.example.myvoozkotlin.helpers.show
import com.example.myvoozkotlin.home.helpers.OnTabItemPicked
import com.example.myvoozkotlin.models.TabItem
import com.example.myvoozkotlin.note.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class NoteFragment: Fragment(), OnTabItemPicked {
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(AuthorizationState.UNAUTORIZATE.equals(BaseApp.getAuthState())){
            (binding.root.findViewById(R.id.ll_need_autorization) as View).show()
        }
        else if(AuthorizationState.AUTORIZATE.equals(BaseApp.getAuthState()) || AuthorizationState.GROUP_AUTORIZATE.equals(BaseApp.getAuthState())){
            initTabLayout()
        }
        initToolbar()
    }

    private fun initTabLayout() {
        val items = mutableListOf<TabItem>()
        items.add(TabItem("Активные", 0, true))
        items.add(TabItem("Выполненные", 0, false))

        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab, position ->
            run {
                tab.text = items.get(position).name

                val textView = LayoutInflater.from(requireContext()).inflate(R.layout.item_tabitem, null)
                (textView as TextView).text = items.get(position).name
                tab.setId(position)
                if(items[position].isActive){
                    textView.setTextColor(resources.getColor(R.color.textLink))
                }
                tab.customView = textView
            }
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                (tab!!.customView as TextView).setTextColor(resources.getColor(R.color.textLink))
                if(tab.id == 0){

                }
                else{

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                (tab!!.customView as TextView).setTextColor(resources.getColor(R.color.textPrimary))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    private fun initToolbar() {
        binding.toolbar.title = resources.getString(R.string.title_note)
        addBackButton()
    }

    private fun addBackButton(){
        binding.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_left)
        binding.toolbar.setNavigationOnClickListener {
            Navigation.findNavController(binding.root).popBackStack()
        }
    }

    override fun onTabItemClick(position: Int) {

    }
}
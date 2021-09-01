package com.example.myvoozkotlin.groupOfUser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homelibrary.model.UserShort
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.data.db.realmModels.AuthUserModel
import com.example.myvoozkotlin.databinding.FragmentGouUserListBinding
import com.example.myvoozkotlin.groupOfUser.adapter.UserListGouAdapter
import com.example.myvoozkotlin.groupOfUser.helpers.OnUserListItemPicked
import com.example.myvoozkotlin.groupOfUser.viewModels.GroupOfUserViewModel
import com.example.myvoozkotlin.helpers.*
import com.example.myvoozkotlin.user.presentation.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListGroupOfUserFragment : Fragment(), OnUserListItemPicked {
    companion object {
        fun newInstance(): UserListGroupOfUserFragment {
            return UserListGroupOfUserFragment()
        }
    }

    private val userViewModel: UserViewModel by viewModels()
    private val groupOfUserViewModel: GroupOfUserViewModel by viewModels()
    private var authUserModel: AuthUserModel? = null

    private var _binding: FragmentGouUserListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGouUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
        initObservers()
        setListeners()
        authUserModel = userViewModel.getCurrentAuthUser()
        groupOfUserViewModel.getUserList(authUserModel!!.accessToken, authUserModel!!.id)
        setPaddingTopMenu()
    }

    private fun configureViews(){
        initToolbar()
    }

    private fun setPaddingTopMenu() {
        binding.root.setPadding(0, UtilsUI.getStatusBarHeight(resources), 0, 0)
    }

    private fun setListeners(){
        parentFragmentManager.setFragmentResultListener(UserListFuncDialogFragment.REQUEST_MAKE_HEAD, this) { key, bundle ->
            //(binding.rvUserList.adapter as? UserListGouAdapter)!!.removeUser(bundle.getInt(UserListFuncDialogFragment.CONSTANT_ID_USER))
        }

        parentFragmentManager.setFragmentResultListener(UserListFuncDialogFragment.REQUEST_REMOVE_USER, this) { key, bundle ->
            (binding.rvUserList.adapter as? UserListGouAdapter)!!.removeUser(bundle.getInt(UserListFuncDialogFragment.CONSTANT_ID_USER))
        }
    }

    private fun initObservers() {
        observeOnCreateGroupOfUserResponse()
    }

    private fun observeOnCreateGroupOfUserResponse() {
        groupOfUserViewModel.userListResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.show()
                }
                Status.SUCCESS -> {
                    binding.progressBar.hide()
                    binding.rvUserList.show()

                    if (it.data == null) {

                    } else {
                        initSearchAdapter(it.data)
                    }
                }
                Status.ERROR -> {
                    binding.progressBar.hide()
                }
            }
        })
    }

    private fun initSearchAdapter(userList: List<UserShort>) {
        if (binding.rvUserList.adapter == null) {
            binding.rvUserList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.rvUserList.adapter = UserListGouAdapter(userList, authUserModel!!, this)
            (binding.rvUserList.adapter as? UserListGouAdapter)?.update(userList)
        } else {
            (binding.rvUserList.adapter as? UserListGouAdapter)?.update(userList)
        }
    }

    private fun initToolbar() {
        binding.toolbar.title = getString(R.string.title_gou_user_list)
        addBackButton()
    }

    private fun addBackButton(){
        binding.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_left)
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onUserListItemClick(userShort: UserShort) {
        val fragment = UserListFuncDialogFragment()
        fragment.arguments = bundleOf(UserListFuncDialogFragment.CONSTANT_ID_USER to userShort.id)
        fragment.show(parentFragmentManager,
            UserListFuncDialogFragment::javaClass.javaClass.simpleName)
    }
}
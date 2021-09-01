package com.example.myvoozkotlin.notification.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homelibrary.model.Notification
import com.example.myvoozkotlin.databinding.FragmentNotificationBaseTabBinding
import com.example.myvoozkotlin.helpers.*
import com.example.myvoozkotlin.user.presentation.viewModel.UserViewModel
import com.example.myvoozkotlin.models.PhotoItem
import com.example.myvoozkotlin.notification.adapter.NotificationAdapter
import com.example.myvoozkotlin.notification.presentation.viewModel.NotificationViewModel
import com.example.myvoozkotlin.photo.PhotoFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NotificationBaseTabFragment: Fragment(), (Notification) -> Unit {
    private val userViewModel: UserViewModel by viewModels()
    private val notificationViewModel: NotificationViewModel by viewModels()

    var type: Int = 0

    companion object {
        private const val ID_TYPE = "id_type"
        fun newInstance(type: Int): NotificationBaseTabFragment {
            val fragment = NotificationBaseTabFragment()
            fragment.arguments = bundleOf(ID_TYPE to type)
            return fragment
        }
    }

    private var _binding: FragmentNotificationBaseTabBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBaseTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
        setListeners()
        initIntentData()
        initObservers()
        val authUserModel = userViewModel.getCurrentAuthUser()

        when (Utils.getAuthorisationState(authUserModel)) {
            AuthorizationState.GROUP_AUTORIZATE -> {
                notificationViewModel.loadUserNotification(authUserModel!!.accessToken, authUserModel.id, type)
            }
            AuthorizationState.AUTORIZATE -> {
                notificationViewModel.loadUserWithUniversityNotification(authUserModel!!.accessToken, authUserModel.id, type, authUserModel.idUniversity)
            }
            AuthorizationState.UNAUTORIZATE -> {
                notificationViewModel.loadUniversityNotification(userViewModel.getIdUniversity())
            }
        }
    }

    private fun openPhotoFragment(photoItem: PhotoItem, position: Int){
        val intent = Intent(requireContext(), PhotoFragment::class.java)
        intent.putExtra(PhotoFragment.BUNDLE_PHOTO, photoItem)
        intent.putExtra(PhotoFragment.CONSTANT_ID_PHOTO, position)
        startActivity(intent)
    }

    private fun initIntentData(){
        type = requireArguments().getInt(ID_TYPE, 0)
    }

    private fun configureViews() {

    }


    private fun setListeners(){

    }

    private fun initObservers() {
        observeOnNotificationListUserResponse()
    }

    private fun observeOnNotificationListUserResponse() {
        notificationViewModel.notificationListResponse.observe(viewLifecycleOwner, {

            when (it.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    binding.progressBar.hide()
                    if (it.data.isNullOrEmpty()) {
                        binding.layoutEmpty.llEmpty.show()
                        binding.rvNotification.hide()
                    } else {
                        binding.layoutEmpty.llEmpty.hide()
                        binding.rvNotification.show()
                        initNotificationAdapter(it.data)
                    }
                }
                Status.ERROR -> {
                    binding.progressBar.hide()
                }
            }
        })
    }

    private fun initNotificationAdapter(notifications: List<Notification>) {
        if (binding.rvNotification.adapter == null) {
            binding.rvNotification.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.rvNotification.adapter = NotificationAdapter(notifications.toMutableList(), this)
        } else {
            (binding.rvNotification.adapter as? NotificationAdapter)?.update(notifications.toMutableList())
        }
    }

    override fun invoke(notification: Notification) {
        openPhotoFragment(notification.images[0], 0)
    }
}
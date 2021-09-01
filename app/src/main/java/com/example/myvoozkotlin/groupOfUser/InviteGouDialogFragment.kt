package com.example.myvoozkotlin.groupOfUser

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.myvoozkotlin.MainActivity
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.data.db.realmModels.AuthUserModel
import com.example.myvoozkotlin.databinding.DialogFragmentGouInviteBinding
import com.example.myvoozkotlin.groupOfUser.viewModels.GroupOfUserViewModel
import com.example.myvoozkotlin.helpers.Status
import com.example.myvoozkotlin.helpers.UtilsUI
import com.example.myvoozkotlin.helpers.hide
import com.example.myvoozkotlin.helpers.show
import com.example.myvoozkotlin.user.presentation.viewModel.UserViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class InviteGouDialogFragment: BottomSheetDialogFragment() {
    private var _binding: DialogFragmentGouInviteBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by viewModels()
    private val groupOfUserViewModel: GroupOfUserViewModel by viewModels()
    private var authUserModel: AuthUserModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransparentBottomSheet)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DialogFragmentGouInviteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authUserModel = userViewModel.getCurrentAuthUser()
        groupOfUserViewModel.getEntryLink(authUserModel!!.accessToken, authUserModel!!.id)

        configureViews()
        initObservers()
        setListeners()
    }

    private fun configureViews() {
        setPaddingTopMenu()
    }

    private fun setPaddingTopMenu() {
        binding.root.setPadding(0, UtilsUI.getStatusBarHeight(resources), 0, 0)
    }

    private fun initObservers() {
        observeEntryLinkResponse()
        observeLockEntryLinkResponse()
        observeUpdateEntryLinkResponse()
    }

    private fun observeEntryLinkResponse() {
        groupOfUserViewModel.getEntryLinkResponse.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    (requireActivity() as MainActivity).showWait(true)
                }
                Status.SUCCESS -> {


                    if (it.data == null) {

                    } else {
                        val calendar = Calendar.getInstance()
                        calendar.timeInMillis = System.currentTimeMillis()


                        val cal = it.data.getDate()!!.clone() as Calendar
                        val cal2 = it.data.getDate()!!.clone() as Calendar

                        cal.add(Calendar.HOUR_OF_DAY, -calendar[Calendar.HOUR_OF_DAY])
                        cal.add(Calendar.MINUTE, -calendar[Calendar.MINUTE])

                        binding.linkTextView.text = it.data.code
                        (requireActivity() as MainActivity).showWait(false)
                        initLock(it.data.isLock)

                        if(it.data.code.isEmpty()){
                            binding.validityTextview.setText("Попросите сгенерировать ссылку своего старосту.")
                            binding.linkTextView.setText("-")
                        }
                        else{
                            if (cal2.after(calendar)) {
                                if (cal[Calendar.HOUR_OF_DAY] == 0 && cal[Calendar.MINUTE] == 0) {
                                    binding.validityTextview.setText("Через 24 ч. 00 м. ссылка станет не действительна")
                                } else {
                                    binding.validityTextview.setText("Через " + cal[Calendar.HOUR_OF_DAY] + " ч. " + cal[Calendar.MINUTE] + " м. ссылка станет не действительна")
                                }
                            } else {
                                binding.validityTextview.setText("Ссылка недействительна. Попросите старосту сгенерировать новую.")
                            }
                            binding.linkTextView.setText(it.data.code)
                        }
                    }
                }
                Status.ERROR -> {
                    //binding.progressBar.hide()
                }
            }
        })
    }

    private fun observeUpdateEntryLinkResponse() {
        groupOfUserViewModel.updateEntryLinkResponse.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    (requireActivity() as MainActivity).showWait(true)
                }
                Status.SUCCESS -> {


                    if (it.data == null) {

                    } else {
                        binding.linkTextView.text = it.data.code
                        (requireActivity() as MainActivity).showWait(false)
                        initLock(it.data.isLock)
                        binding.validityTextview.text = "Через 24 ч. 00 м. ссылка станет не действительна"
                    }
                }
                Status.ERROR -> {
                    //binding.progressBar.hide()
                }
            }
        })
    }

    private fun observeLockEntryLinkResponse() {
        groupOfUserViewModel.lockLinkResponse.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    (requireActivity() as MainActivity).showWait(true)
                }
                Status.SUCCESS -> {


                    if (it.data == null) {

                    } else {
                        (requireActivity() as MainActivity).showWait(false)
                    }
                }
                Status.ERROR -> {
                    //binding.progressBar.hide()
                }
            }
        })
    }

    private fun setListeners() {
        binding.cvUpdateButton.setOnClickListener {
            groupOfUserViewModel.updateEntryLink(authUserModel!!.accessToken, authUserModel!!.id)
        }

        binding.cvCopyBtn.setOnClickListener {
            val clipboard =
                requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("", binding.linkTextView.text.toString())
            clipboard.setPrimaryClip(clip)
            UtilsUI.makeToast(getString(R.string.toast_gou_invite_link_copy))
        }

        binding.lockButton.setOnClickListener {
            groupOfUserViewModel.lockEntryLink(authUserModel!!.accessToken, authUserModel!!.id, 0)
            binding.lockButton.hide()
            binding.lockOpenButton.show()
            initLock(false)
        }

        binding.lockOpenButton.setOnClickListener {
            groupOfUserViewModel.lockEntryLink(authUserModel!!.accessToken, authUserModel!!.id, 1)
            binding.lockButton.show()
            binding.lockOpenButton.hide()
            initLock(true)
        }
    }

    private fun initLock(state: Boolean){
        if(state){
            binding.apply {
                lockButton.show()
                lockOpenButton.hide()
                blockTextview.show()
                validityTextview.hide()
                cvUpdateButton.hide()
                inviteLinkBlock.hide()
            }
        }
        else{
            binding.apply {
                lockButton.hide()
                lockOpenButton.show()
                blockTextview.hide()
                validityTextview.show()
                cvUpdateButton.show()
                inviteLinkBlock.show()
            }
        }

        if(authUserModel!!.id != authUserModel!!.groupOfUser!!.idOlder){
            binding.apply {
                cvUpdateButton.hide()
                lockOpenButton.hide()
                lockButton.hide()
                blockTextview.text = "Ссылка-приглашение заблокирована"
            }
        }
    }
}
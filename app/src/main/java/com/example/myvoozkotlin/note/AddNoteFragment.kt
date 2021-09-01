package com.example.myvoozkotlin.note

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.*
import com.android.volley.toolbox.Volley
import com.example.myvoozkotlin.BaseApp
import com.example.myvoozkotlin.MainActivity
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.databinding.FragmentAddNoteBinding
import com.example.myvoozkotlin.helpers.*
import com.example.myvoozkotlin.helpers.adapters.RadioButtonAdapter
import com.example.myvoozkotlin.helpers.filePath.VolleyMultipartRequest
import com.example.myvoozkotlin.home.DatePickerDialogFragment
import com.example.myvoozkotlin.home.TimePickerDialogFragment
import com.example.myvoozkotlin.home.helpers.OnDatePicked
import com.example.myvoozkotlin.home.helpers.OnTabItemPicked
import com.example.myvoozkotlin.home.helpers.OnTimePicked
import com.example.myvoozkotlin.user.presentation.viewModel.UserViewModel
import com.example.myvoozkotlin.models.PhotoItem
import com.example.myvoozkotlin.models.RadioItem
import com.example.myvoozkotlin.models.SearchItem
import com.example.myvoozkotlin.note.helpers.OnPhotoPicked
import com.example.myvoozkotlin.note.viewModels.NoteViewModel
import com.example.myvoozkotlin.photo.PhotoFragment
import com.example.myvoozkotlin.photo.adapters.PhotoAdapter
import com.example.myvoozkotlin.search.SearchFragment
import com.example.myvoozkotlin.search.helpers.OnSearchItemPicked
import com.example.myvoozkotlin.search.helpers.SearchEnum
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm.getApplicationContext
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddNoteFragment: Fragment(), OnTabItemPicked, OnSearchItemPicked, OnPhotoPicked,
    OnDatePicked, OnTimePicked {

    companion object {
        const val PHOTO_SELECT_REQUEST_CODE = 1
        const val REQUEST_NOTE = "response_note"
        const val CONSTANT_NOTE = "note"
        const val MAX_PHOTO_COUNT = 4
        fun newInstance(): AddNoteFragment {
            return AddNoteFragment()
        }
        private lateinit var rQueue: RequestQueue
        private var photoCount = 0

        fun uploadImage(
            bitmap: Bitmap,
            context: Context,
            binding: FragmentAddNoteBinding,
            addNoteFragment: AddNoteFragment
        ) {
            val scaledBitmap: Bitmap = scaleDown(
                bitmap,
                1000f,
                true
            )!!
            if (scaledBitmap.byteCount <= 5242880) {
                photoCount++
                binding.cvAddPhoto.ivLoad.show()
                val animationRotateCorner = AnimationUtils.loadAnimation(
                    context, R.anim.rotate_infinity
                )
                binding.cvAddPhoto.ivLoad.startAnimation(animationRotateCorner)
                binding.cvAddPhoto.ivPhoto.hide()
                binding.cvAddPhoto.root.isEnabled = false
                val volleyMultipartRequest: VolleyMultipartRequest =
                    object : VolleyMultipartRequest(
                        Method.POST, Constants.BASE_URL + "profile?",
                        object : Response.Listener<NetworkResponse>{
                            override fun onResponse(response: NetworkResponse) {
                                rQueue.cache.clear()
                                try {
                                    val jsonObject = JSONObject(String(response.data))
                                    jsonObject.toString().replace("\\\\", "")
                                    if (jsonObject.getString("status") == "true") {
                                        val url: String = jsonObject.getString("path")
                                        val fullPath: String = jsonObject.getString("full_path")
                                        val id: Int = jsonObject.getInt("id")

                                        binding.cvAddPhoto.root.isEnabled = true
                                        binding.cvAddPhoto.ivLoad.clearAnimation()
                                        binding.cvAddPhoto.ivLoad.hide()
                                        binding.cvAddPhoto.ivPhoto.show()

                                        addNoteFragment.initPhotoAdapter(PhotoItem(url, fullPath, id))

                                    }
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                            }

                        },
                        Response.ErrorListener { error ->
                            Toast.makeText(
                                getApplicationContext(),
                                error.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }) {

                        @Throws(AuthFailureError::class)
                        override fun getParams(): Map<String, String>? {
                            // params.put("tags", "ccccc");  add string parameters
                            return HashMap()
                        }

                        override fun getByteData(): Map<String, DataPart>? {
                            val params: MutableMap<String, DataPart> = HashMap()
                            val imagename = System.currentTimeMillis()
                            params["filename"] =
                                DataPart("$imagename.png", getFileDataFromDrawable(scaledBitmap))
                            return params
                        }
                    }
                volleyMultipartRequest.retryPolicy = DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
                rQueue = Volley.newRequestQueue(context)
                rQueue.add(volleyMultipartRequest)
            } else {
                Toast.makeText(
                    getApplicationContext(),
                    "Файл должен быть не более 5 мб",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        private fun getFileDataFromDrawable(bitmap: Bitmap): ByteArray? {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream)
            return byteArrayOutputStream.toByteArray()
        }


        private fun scaleDown(
            realImage: Bitmap, maxImageSize: Float,
            filter: Boolean
        ): Bitmap? {
            val ratio = Math.min(
                maxImageSize / realImage.width,
                maxImageSize / realImage.height
            )
            val width = Math.round(ratio * realImage.width)
            val height = Math.round(ratio * realImage.height)
            return Bitmap.createScaledBitmap(
                realImage, width,
                height, filter
            )
        }
    }

    private val userViewModel: UserViewModel by viewModels()
    private val noteViewModel: NoteViewModel by viewModels()
    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!
    private var idObject = 0

    private lateinit var calendar: Calendar
    private lateinit var currentCalendar: Calendar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun requestMultiplePermissions() {
        Dexter.withActivity(requireActivity())
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        val photoPickerIntent = Intent(Intent.ACTION_GET_CONTENT)
                        photoPickerIntent.addCategory(Intent.CATEGORY_OPENABLE)
                        photoPickerIntent.type = "image/*"
                        startActivityForResult(photoPickerIntent, PHOTO_SELECT_REQUEST_CODE)
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken?
                ) {
                }
            }).withErrorListener {
                Toast.makeText(
                    getApplicationContext(),
                    "Произошла какая-то бяка",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .onSameThread()
            .check()
    }

    private fun setPaddingTopMenu() {
        binding.toolbar.setPadding(0, UtilsUI.getStatusBarHeight(resources), 0, 0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
        setListeners()
        initObservers()

        initToolbar()

        calendar = Calendar.getInstance()
        currentCalendar = calendar.clone() as Calendar

        val radioItem1 = RadioItem("Личное", 1, true)
        val radioItem2 = RadioItem("ИБ-416", 0, false)
        val listTypeAccess = listOf(radioItem1, radioItem2)
        initTypeAccessAdapter(listTypeAccess)
    }

    private fun setLoadStateBtn(){
        binding.ivLoadingSaveButton.show()
        val rotationAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_infinity)
        binding.ivLoadingSaveButton.startAnimation(rotationAnimation)
        binding.cvSaveButton.isEnabled = false
    }

    private fun setOpenStateBtn(){
        binding.ivLoadingSaveButton.hide()
        binding.ivLoadingSaveButton.clearAnimation()
        binding.cvSaveButton.isEnabled = true
    }

    private fun configureViews() {
        setPaddingTopMenu()
    }

    private fun setListeners(){
        binding.cvAddPhoto.root.setOnClickListener {
            if(MAX_PHOTO_COUNT > photoCount){
                requestMultiplePermissions()
            }
            else{
                UtilsUI.makeToast(getString(R.string.error_max_photo_count))
            }
        }

        val authUserModel = userViewModel.getCurrentAuthUser()
        binding.cvSaveButton.setOnClickListener {

            if(binding.etShortText.text.trim().isNotEmpty() || binding.etLongText.text.trim().isNotEmpty()){
                if(idObject != 0){
                    if(!calendar.equals(currentCalendar)){
                        setLoadStateBtn()
                        var photoItems = listOf<Int>()
                        if((binding.rvPhoto.adapter as? PhotoAdapter) != null)
                            photoItems = (binding.rvPhoto.adapter as? PhotoAdapter)!!.getItems()

                        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
                        noteViewModel.addNote(
                            authUserModel!!.accessToken,
                            authUserModel.id,
                            idObject,
                            binding.etShortText.text.toString(),
                            binding.etLongText.text.toString(),
                            dateFormat.format(calendar.time),
                            (binding.rvTypeAccess.adapter as? RadioButtonAdapter)?.getSelectItemPosition()!!,
                            photoItems

                        )
                    }
                    else
                        UtilsUI.makeToast("Обязательно выберите время")
                }
                else
                    UtilsUI.makeToast("Обязательно выберите предмет")
            }
            else
                UtilsUI.makeToast("Не забудьте ввести текст")
        }

        binding.clObjectButton.setOnClickListener {
            loadSearchFragment(SearchEnum.OBJECT.ordinal, BaseApp.getSharedPref().getInt(Constants.APP_PREFERENCES_USER_UNIVERSITY_ID, 0))
        }

        binding.clTimeButton.setOnClickListener {
            fragmentManager?.let {
                DatePickerDialogFragment.newInstance(calendar, this)
                    .show(it, DatePickerDialogFragment.javaClass.simpleName)
            }
        }

        parentFragmentManager.setFragmentResultListener(SearchFragment.REQUEST_OBJECT, this) { key, bundle ->
            val name = bundle.getString("fullName", "null")
            binding.tvObjectName.text = name
            idObject = bundle.getInt("id")
        }

        parentFragmentManager.setFragmentResultListener(PhotoFragment.REQUEST_DELETE_PHOTO, this) { _, bundle ->
            (binding.rvPhoto.adapter as? PhotoAdapter)?.removeItem(bundle.getInt("idPhoto"))
        }
    }

    private fun initObservers() {
        observeOnAddNoteResponse()
    }

    private fun observeOnAddNoteResponse() {
        noteViewModel.addNoteResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                    (requireActivity() as MainActivity).showWait(true)
                }
                Status.SUCCESS -> {
                    (requireActivity() as MainActivity).showWait(false)
                    val bundle = Bundle()
                    bundle.putSerializable(CONSTANT_NOTE, it.data)

                    parentFragmentManager.setFragmentResult(REQUEST_NOTE, bundle)
                    requireActivity().onBackPressed()
                    UtilsUI.makeToast(getString(R.string.success_create_note))
                }
                Status.ERROR -> {
                    setOpenStateBtn()
                }
            }
        })
    }

    private fun initTypeAccessAdapter(item: List<RadioItem>) {
        if (binding.rvTypeAccess.adapter == null) {
            binding.rvTypeAccess.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvTypeAccess.adapter = RadioButtonAdapter(item, this)
        } else {
            (binding.rvTypeAccess.adapter as? RadioButtonAdapter)?.update(item)
        }
    }

    private fun initPhotoAdapter(item: PhotoItem) {
        if (binding.rvPhoto.adapter == null) {
            binding.rvPhoto.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvPhoto.adapter = PhotoAdapter( this)
            (binding.rvPhoto.adapter as? PhotoAdapter)?.addItem(item)
        } else {
            (binding.rvPhoto.adapter as? PhotoAdapter)?.addItem(item)
        }
    }

    private fun loadSearchFragment(typeSearch: Int, addParam: Int){
        val fragment = SearchFragment.newInstance(typeSearch, addParam)
        parentFragmentManager.beginTransaction()
            .addToBackStack("addNote")
            .replace(R.id.addNoteRootView, fragment, SearchFragment.javaClass.simpleName).commit()
    }

    private fun loadPhotoFragment(photoItem: PhotoItem, position: Int){
        val intent = Intent(requireContext(), PhotoFragment::class.java)
        intent.putExtra(PhotoFragment.BUNDLE_PHOTO, photoItem)
        intent.putExtra(PhotoFragment.CONSTANT_ID_PHOTO, position)
        startActivity(intent)
    }

    private fun initToolbar() {
        binding.toolbar.title = getString(R.string.title_add_note)
        addBackButton()
    }

    private fun addBackButton(){
        binding.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_left)
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onTabItemClick(position: Int) {

    }

    override fun onSearchItemClick(searchItem: SearchItem) {

    }

    override fun onPhotoClick(position: Int) {
        loadPhotoFragment((binding.rvPhoto.adapter as? PhotoAdapter)?.getItem(position)!!, position)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_CANCELED) {
            return
        }
        else if(resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val contentURI = data.data
                try {
                    val bitmap =
                        MediaStore.Images.Media.getBitmap(requireContext().contentResolver, contentURI)
                    uploadImage(bitmap, requireContext(), binding, this)
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDateCalendarClick(calendar: Calendar) {
        val timePickerDialog = TimePickerDialogFragment(calendar, this)
        timePickerDialog.show(parentFragmentManager, "timepicker")
    }

    override fun onTimeClick(calendar: Calendar) {
        this.calendar = calendar
        val ii: String = Utils.getMonthName(calendar[Calendar.MONTH])
        val minutes =
            if (calendar[Calendar.MINUTE] < 10) "0" + calendar[Calendar.MINUTE] else calendar[Calendar.MINUTE].toString()
        binding.tvTimeName.text = Utils.getDayName(calendar[Calendar.DAY_OF_WEEK])
            .toString() + ", " + calendar[Calendar.DAY_OF_MONTH] + "." + ii + " в " + calendar[Calendar.HOUR_OF_DAY] + ":" + minutes
    }
}
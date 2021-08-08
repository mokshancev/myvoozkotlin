package com.example.myvoozkotlin.photo

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.myvoozkotlin.BaseFragment
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.databinding.FragmentPhotoBinding
import com.example.myvoozkotlin.helpers.hide
import com.example.myvoozkotlin.models.PhotoItem
import com.example.myvoozkotlin.search.SearchFragment
import com.example.myvoozkotlin.search.helpers.SearchEnum
import com.example.myvoozkotlin.swipeBackLayout.SwipeBackLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt


@AndroidEntryPoint
class PhotoFragment: AppCompatActivity() {

    companion object {
        const val BUNDLE_PHOTO = "bundle_photo"
        const val CONSTANT_ID_PHOTO = "idPhoto"
        const val REQUEST_DELETE_PHOTO = "deletePhoto"
    }

    private var _binding: FragmentPhotoBinding? = null
    private val binding get() = _binding!!

    lateinit var photoItem: PhotoItem
    private var isShowBar = true
    var idPhoto: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = FragmentPhotoBinding.inflate(layoutInflater)
        setContentView(binding.rootView)
        idPhoto = intent.getIntExtra(CONSTANT_ID_PHOTO, 0)

        configureViews()
        setListeners()
        initToolbar()
        callNotFullScreen()
        binding.rootView.background.alpha = 255
    }

    private fun setListeners(){
        binding.photoView.setOnClickListener {
            if(isShowBar){
                callFullScreen()
            }
            else{
                callNotFullScreen()
            }
            isShowBar = !isShowBar
        }

        binding.swipeBackLayout.setSwipeBackListener(object : SwipeBackLayout.OnSwipeBackListener {
            override fun onViewPositionChanged(
                mView: View?,
                swipeBackFraction: Float,
                swipeBackFactor: Float
            ) {
                if(1*(255*(1-swipeBackFraction)).roundToInt() > 255){
                    binding.rootView.background.alpha = 255
                }
                else{
                    binding.rootView.background.alpha = 1*(255*(1-swipeBackFraction)).roundToInt()
                }

                if(isShowBar){
                    callFullScreen()
                    isShowBar = false
                }
            }


            override fun onViewSwipeFinished(mView: View?, isEnd: Boolean) {
                if (isEnd) {
                    setNormalStatusBar()
                    onBackPressed()
                }

            }
        })
    }

    private fun setNormalStatusBar(){
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        window.navigationBarColor = Color.WHITE
    }

    fun callFullScreen() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        binding.toolbar.animate().translationY((-binding.toolbar.getBottom()).toFloat())
            .setInterpolator(AccelerateInterpolator()).start()
    }

    fun callNotFullScreen() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        binding.toolbar.animate().translationY(0F).setInterpolator(DecelerateInterpolator()).start()
    }

    private fun configureViews() {
        photoItem = intent.getSerializableExtra(BUNDLE_PHOTO) as PhotoItem
        binding.toolbar.setPadding(0, BaseFragment.getStatusBarHeight(resources), 0, 0)
        Glide.with(this)
            .load(photoItem.fullPhoto)
            .listener(object: RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar.hide()
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

            }
            )
            .into(binding.photoView)

        binding.toolbar.inflateMenu(R.menu.menu_delete)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_delete -> {
                    val bundle = Bundle()
                    bundle.putInt(CONSTANT_ID_PHOTO, idPhoto)
                    //parentFragmentManager.setFragmentResult(REQUEST_DELETE_PHOTO, bundle)
                    setNormalStatusBar()
                    onBackPressed()
                }
            }
            true
        }


        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.navigationBarColor = Color.BLACK
        window.statusBarColor = Color.TRANSPARENT
    }

    private fun initToolbar() {
        binding.toolbar.title = ""
        addBackButton()
    }

    private fun addBackButton(){
        val icon = resources.getDrawable(R.drawable.ic_arrow_left)
        binding.toolbar.navigationIcon = icon
        binding.toolbar.setNavigationOnClickListener {
            setNormalStatusBar()
            onBackPressed()
        }
    }
}
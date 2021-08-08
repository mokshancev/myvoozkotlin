package com.example.myvoozkotlin

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.databinding.FragmentPhotoBinding
import com.example.myvoozkotlin.helpers.hide
import com.example.myvoozkotlin.models.PhotoItem
import com.example.myvoozkotlin.swipeBackLayout.SwipeBackLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt


@AndroidEntryPoint
open class BaseFragment: Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //requireView().findViewById<View>(R.id.toolbar).setPadding(0, getStatusBarHeight(resources), 0, 0)
    }

    companion object{
        fun getStatusBarHeight(resources: Resources): Int {
            var result = 0
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = resources.getDimensionPixelSize(resourceId)
            }
            return result
        }
    }
}
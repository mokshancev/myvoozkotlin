package com.example.myvoozkotlin.helpers.forView

import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment

class NewDialog : DialogFragment {
    interface OnBindView {
        fun onView(view: View?)
    }

    interface OnClickItem {
        fun onClick(dialogInterface: NewDialog?, position: Int)
    }

    @LayoutRes
    var layoutRes = 0
    private var v: View? = null
    private var onBindView: OnBindView? = null
    private val onClickItem: OnClickItem? = null

    constructor(layoutRes: Int) {
        this.layoutRes = layoutRes
    }

    constructor(view: View?) {
        this.v = view
    }

    constructor() {}

    fun setOnBindViewListener(onBindView: OnBindView?) {
        this.onBindView = onBindView
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if (view == null && layoutRes == 0) return null
        if (view == null) v = inflater.inflate(layoutRes, container, false)
        if (onBindView != null) onBindView!!.onView(view)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return v
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        dismissAllowingStateLoss()
        super.onConfigurationChanged(newConfig)
    }
}
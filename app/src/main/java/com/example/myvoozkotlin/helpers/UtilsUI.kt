package com.example.myvoozkotlin.helpers

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import com.example.myvoozkotlin.BaseApp
import java.net.URI

object UtilsUI {
    fun makeToast(resId: Int?) {
        Toast.makeText(BaseApp.instance, resId!!, Toast.LENGTH_SHORT).show()
    }

    fun makeToast(text: String) {
        Toast.makeText(BaseApp.instance, text, Toast.LENGTH_SHORT).show()
    }

    fun getStatusBarHeight(resources: Resources): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun convertUriToBitmap(uri: Uri): Bitmap {
        return if (android.os.Build.VERSION.SDK_INT >= 29) {
            val source = ImageDecoder.createSource(
                BaseApp.instance!!.contentResolver,
                uri
            )
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(BaseApp.instance!!.contentResolver, uri)
        }
    }
}
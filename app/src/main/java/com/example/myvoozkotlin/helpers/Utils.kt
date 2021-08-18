package com.example.myvoozkotlin.helpers

import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.Volley
import com.example.myvoozkotlin.BaseApp
import com.example.myvoozkotlin.data.db.DbUtils
import com.example.myvoozkotlin.data.db.RealmUtils
import com.example.myvoozkotlin.helpers.filePath.VolleyMultipartRequest
import io.realm.Realm
import io.realm.RealmConfiguration
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun getDayName(position: Int): String{
        when(position){
            0 -> return "Пн"
            1 -> return "Вт"
            2 -> return "Ср"
            3 -> return "Чт"
            4 -> return "Пт"
            else -> return "Сб"
        }
    }

    fun getMonthName(position: Int): String{
        when(position){
            0 -> return "Янв"
            1 -> return "Фев"
            2 -> return "Мар"
            3 -> return "Апр"
            4 -> return "Май"
            5 -> return "Июн"
            6 -> return "Июл"
            7 -> return "Авг"
            8 -> return "Сен"
            9 -> return "Окт"
            10 -> return "Ноя"
            else -> return "Дек"
        }
    }

    fun getCalendarDayOfWeek(calendar: Calendar, position: Int): Calendar{
        var c = calendar.clone() as Calendar
        when(position){
            0 -> c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            1 -> c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY)
            2 -> c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY)
            3 -> c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY)
            4 -> c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY)
            else -> c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
        }
        return c
    }

    fun stringToCalendar(strDate: String?, timezone: TimeZone?): Calendar? {
        val FORMAT_DATETIME = "yyyy-MM-dd HH:mm"
        val sdf = SimpleDateFormat(FORMAT_DATETIME)
        sdf.timeZone = timezone
        val date = sdf.parse(strDate)
        val cal = Calendar.getInstance()
        cal.time = date
        return cal
    }

    fun uploadImage(bitmap: Bitmap, accessToken: String, idUser: Int, type: String) {
        val scaledBitmap = scaleDown(bitmap, 1000f, true)
        if (scaledBitmap.byteCount <= 5242880) {
            val volleyMultipartRequest: VolleyMultipartRequest = object : VolleyMultipartRequest(
                Method.POST, Constants.BASE_URL + "profile?",
                Response.Listener<NetworkResponse> { response ->
                    try {
                        val jsonObject = JSONObject(String(response.data))
                        jsonObject.toString().replace("\\\\", "")
                        if (jsonObject.getString("status") == "true") {
                            val url = jsonObject.getString("path")

                            if(url != null){
                                val dbUtils = DbUtils(Realm.getDefaultInstance())
                                val authUserModel = dbUtils.getCurrentAuthUser()
                                authUserModel.groupOfUser!!.image = url
                                Log.d("bwebwebweb", url)
                                dbUtils.setCurrentAuthUser(authUserModel)
                            }
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    UtilsUI.makeToast("Ошибка загрузки изображения")
                }) {
                /*
                  * If you want to add more parameters with the image
                  * you can do it here
                  * here we have only one parameter with the image
                  * which is tags
                  * */

                override fun getParams(): MutableMap<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["type"] = type
                    params["access_token"] = accessToken
                    params["user_id"] = idUser.toString()
                    return params
                }

                override fun getByteData(): MutableMap<String, DataPart> {
                    val params: MutableMap<String, DataPart> = HashMap<String, DataPart>()
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
            val rQueue = Volley.newRequestQueue(BaseApp.instance) as RequestQueue
            rQueue.add(volleyMultipartRequest)
        } else {
            UtilsUI.makeToast("Файл не может быть больше 5 МБайт")
        }
    }

    fun buildImageBodyPart(fileName: String, bitmap: Bitmap):  MultipartBody.Part {
        val leftImageFile = convertBitmapToFile(fileName, bitmap)
        val reqFile = RequestBody.create("image/*".toMediaTypeOrNull(),    leftImageFile)
        return MultipartBody.Part.createFormData(fileName,     leftImageFile.name, reqFile)}

    fun convertBitmapToFile(fileName: String, bitmap: Bitmap): File {
        //create a file to write bitmap data
        val file = File(BaseApp.instance!!.cacheDir, fileName)
        file.createNewFile()

        //Convert bitmap to byte array
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos)
        val bitMapData = bos.toByteArray()

        //write the bytes in file
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        try {
            fos?.write(bitMapData)
            fos?.flush()
            fos?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }
}
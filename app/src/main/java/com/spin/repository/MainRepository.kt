package com.spin.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.spin.AppApplication
import com.spin.api.RetrofitBuilder.imagesAPI
import com.spin.models.Images

object MainRepository {

    private var PRIVATE_MODE = 0
    private val PREF_NAME = "fav_list"
    val sharedPref: SharedPreferences?
    val editor : SharedPreferences.Editor ?
    val listOfFav : ArrayList<String>
    val SP_KEY = "FAV_LIST"

    init {
        sharedPref= AppApplication.applicationContext()?.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = sharedPref?.edit()
        listOfFav = getArrayListOfFav(SP_KEY)
    }

    suspend fun  getAllImages() : Images{
        return imagesAPI.getImages()
    }

    fun getArrayListOfFav(key: String? = SP_KEY): ArrayList<String> {
        val gson = Gson()
        val json = sharedPref?.getString(key, null)
        if(json == null){
            return ArrayList<String>()
        }
        val type = object : TypeToken<ArrayList<String?>?>() {}.type
        return gson.fromJson(json, type)
    }

    fun saveArrayList(list: ArrayList<String>, key: String?) {
        val editor = sharedPref?.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor?.putString(key, json)
        editor?.apply() // This line is IMPORTANT !!!
    }

}
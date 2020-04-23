package com.spin.adapters

import android.app.Activity
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.spin.R
import com.spin.models.Image
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.image_list_item.view.*
import kotlin.math.roundToInt


class ImagesAdapter(var images: ArrayList<Image> = ArrayList(), context: Activity? , val isFav : Boolean) :
    RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {
    private val picasso = Picasso.get()
    val width: Int
    val height: Int

    private var PRIVATE_MODE = 0
    private val PREF_NAME = "fav_list"
    val sharedPref: SharedPreferences?
    val editor :SharedPreferences.Editor ?
    val listOfFav : ArrayList<String>
    val SP_KEY = "FAV_LIST"

    init {
        val displayMetrics = DisplayMetrics()
        context?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        /**
         * get the width of screen in order to resize the images accordingly
         */
        width = (displayMetrics.widthPixels / 2.2).roundToInt()
        height = width
        sharedPref= context?.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = sharedPref?.edit()
        listOfFav = getArrayListOfFav(SP_KEY)
        if(isFav){
            val favImages = ArrayList<Image>()
            for (image in images){
                if(listOfFav.contains(image.url)){
                    favImages.add(image)
                }
            }
            images = favImages
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.image_list_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int = if(isFav)listOfFav.size else images.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class ImageViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun onBind(position: Int) {

            val image = images[position]
            val ivContent = view.ivContent
            image.url?.let {
                picasso.load(it)
                    .error(R.drawable.ic_image_white_12dp)
                    .placeholder(R.drawable.ic_image_default)
                    .resize(width, height)
                    .into(ivContent, object : Callback {
                        override fun onSuccess() {
                        }

                        override fun onError(e: Exception?) {
                        }
                    })
            }
            view.tvTitle.text = image.title ?: ""
            val textCopy = if(listOfFav.contains(image.url)) "remove from Fav" else "add to Fav"
            view.btnFavorite.text = textCopy
            view.btnFavorite.setOnClickListener {// add /remove to the favorite
                image.url?.let {
                    if(listOfFav.contains(image.url)){
                        listOfFav.remove(image.url)
                        view.btnFavorite.text = "add to Fav"
                    }
                    else{
                        listOfFav.add(image.url)
                        view.btnFavorite.text = "remove from Fav"
                    }
                    saveArrayList(listOfFav,SP_KEY)
                }
            }
        }
    }

    fun updateImages(list: ArrayList<Image>) {
        if(!isFav) {
            if (!list.isNullOrEmpty()) {
                images.clear()
                images.addAll(list)
                notifyDataSetChanged()
            }
        }
    }

    fun getArrayListOfFav(key: String?): ArrayList<String> {
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

interface  OnImageClickListener{
    fun onImageClicked(position: Int)
}
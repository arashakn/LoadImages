package com.spin.adapters

import android.app.Activity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spin.R
import com.spin.models.Image
import com.spin.repository.MainRepository
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.image_list_item.view.*
import java.lang.Exception
import kotlin.math.roundToInt

class ImagesAdapter(val images: ArrayList<Image> = ArrayList(), context: Activity?) :
    RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {
    private val picasso = Picasso.get()
    val width: Int
    val height: Int
    var fav : ArrayList<String>

    init {
        val displayMetrics = DisplayMetrics()
        context?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        /**
         * get the width of screen in order to resize the images accordingly
         */
        width = (displayMetrics.widthPixels / 2.2).roundToInt()
        height = width
        fav = MainRepository.getArrayListOfFav()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.image_list_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int = images.size

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
            val textCopy = if(fav.contains(image.url)) "remove from Fav" else "add to Fav"
            view.btnFav.text = textCopy
            view.btnFav.setOnClickListener {
                if(image.url != null) {
                    if (fav.contains(image.url)) {
                        fav.remove(image.url)
                        it.btnFav.text = "add to Fav"
                    } else {
                        fav.add(image?.url)
                        view.btnFav.text = "remove from Fav"
                    }
                    MainRepository.saveArrayList(fav,MainRepository.SP_KEY)
                }
            }
            }
    }

    fun updateImages(list: ArrayList<Image>) {
        if (!list.isNullOrEmpty()) {
            images.clear()
            images.addAll(list)
            notifyDataSetChanged()
        }
    }
}
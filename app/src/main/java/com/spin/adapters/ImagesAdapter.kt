package com.spin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spin.R
import com.spin.models.Image
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.image_list_item.view.*

class ImagesAdapter ( val images : ArrayList<Image> = ArrayList()) : RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_list_item ,parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class ImageViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
        fun onBind(position: Int) {
            val image = images[position]
            val ivContent = view.ivContent
            image.url?.let {
                Picasso.get()
                    .load(it)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(ivContent);
            }
            view.tvTitle.text =  image?.title ?:""
        }
    }

    fun updateImages(list : ArrayList<Image>){
        if(!list.isNullOrEmpty()) {
            images.clear()
            images.addAll(list)
            notifyDataSetChanged()
        }
    }
}
package com.spin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.spin.R
import com.spin.adapters.ImagesAdapter
import kotlinx.android.synthetic.main.images_list_fragment.*

class ImagesListFragment : Fragment() {

    companion object {
        fun newInstance() = ImagesListFragment()
    }

    private lateinit var viewModel: ImagesListViewModel
    private lateinit var imagesAdapter : ImagesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.images_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ImagesListViewModel::class.java)
        imagesAdapter = ImagesAdapter()
        rvImages.adapter = imagesAdapter
        rvImages.layoutManager = LinearLayoutManager(context)
        observeViewModel()
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchImages()
    }
    private fun observeViewModel(){
        viewModel.images.observe(viewLifecycleOwner, Observer{
            it?.let {
                imagesAdapter.updateImages(it)
            }
        })
    }






}

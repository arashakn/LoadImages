package com.spin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.spin.R
import com.spin.adapters.ImagesAdapter
import kotlinx.android.synthetic.main.images_list_fragment.*

class ImagesListFragment : Fragment() {
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

    private fun observeViewModel(){
        viewModel.allImages.observe(viewLifecycleOwner, Observer {
            it?.let {
                imagesAdapter.updateImages(it.data)
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer{
            it?.let {
                Toast.makeText(activity,"Error!",Toast.LENGTH_LONG).show()
            }
        })
    }


    override fun onResume() {
        super.onResume()
    }

}

package com.spin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.spin.R
import com.spin.adapters.ImagesAdapter
import kotlinx.android.synthetic.main.images_list_fragment.*

/**
 * Main Fragment in order to display list of images
 */
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
        activity?.let {
            viewModel = ViewModelProvider(it).get(ImagesListViewModel::class.java)//makes the ViewModel scoop to activity rather than fragment
            imagesAdapter = ImagesAdapter(context = activity)
            rvImages.apply {
                adapter = imagesAdapter
                layoutManager = GridLayoutManager(activity, 2)
            }
            observeViewModel()
        }
    }

    /**
     * observing live data in Fragment
     */
    private fun observeViewModel(){
        viewModel.allImages.observe(viewLifecycleOwner, Observer {
            it?.let {
                imagesAdapter.updateImages(it.data)
            }
        })
        viewModel.error.observe(viewLifecycleOwner, Observer{
            it?.let {
                if(it) {
                    Toast.makeText(activity, "Network Error!", Toast.LENGTH_LONG).show() // In case of network error, a message will be displayed to the user
                }
            }
        })
    }
}

package com.lyj.ituensapp.ui.main.fragments.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lyj.ituensapp.R
import com.lyj.ituensapp.core.extension.lang.simpleTag

class ListFragment : Fragment() {

    companion object {
        private var instance : ListFragment? = null
        fun getInstance() : ListFragment = instance ?: ListFragment().apply {
            Log.d(simpleTag,"생성됨")
            instance = this
        }
    }

    private lateinit var viewModel: ListFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListFragmentViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
package com.lyj.itunesapp.core.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<DATA_SOURCE, VIEW_HOLDER : RecyclerView.ViewHolder>(
    internal open val viewModel : AdapterViewModel<DATA_SOURCE>,
    @LayoutRes private val layoutId : Int,
) : RecyclerView.Adapter<VIEW_HOLDER>() {

    abstract fun generateViewHolder(view : View) : VIEW_HOLDER

    abstract fun onBindViewHolder(holder : VIEW_HOLDER, position: Int, data : DATA_SOURCE)

    override fun onBindViewHolder(holder: VIEW_HOLDER, position: Int) {
        onBindViewHolder(holder,position,viewModel.items.toList()[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VIEW_HOLDER {
        val view = LayoutInflater.from(parent.context).inflate(layoutId,parent,false)
        return generateViewHolder(view)
    }

    override fun getItemCount(): Int = viewModel.itemCount
}



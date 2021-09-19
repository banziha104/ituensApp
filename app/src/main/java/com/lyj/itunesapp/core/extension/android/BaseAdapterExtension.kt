package com.lyj.itunesapp.core.extension.android

import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lyj.itunesapp.core.base.BaseAdapter

fun <T,V  : RecyclerView.ViewHolder> BaseAdapter<T,V>.resString(
    @StringRes resId: Int
): String = viewModel.context.getString(resId)


fun <T,V  : RecyclerView.ViewHolder> BaseAdapter<T,V>.resDimen(
    @DimenRes resId: Int
): Float = viewModel.context.resources.getDimension(resId)


fun <T,V  : RecyclerView.ViewHolder> BaseAdapter<T,V>.resColor(
    @ColorRes resId: Int
): Int = ContextCompat.getColor(viewModel.context,resId)

fun <T,V  : RecyclerView.ViewHolder> BaseAdapter<T,V>.resDrawable(
    @DrawableRes resId: Int
): Drawable = ContextCompat.getDrawable(viewModel.context,resId)!!
package com.gnecmedia.appentus.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gnecmedia.appentus.R
import com.gnecmedia.appentus.databinding.CountriesListItemBinding
import com.gnecmedia.appentus.models.Picture
import kotlinx.android.extensions.LayoutContainer

class PictureListAdapter : RecyclerView.Adapter<PictureListAdapter.ViewHolder>() {

    private var mList: List<Picture>? = listOf()

    fun setData(list: List<Picture>) {
        mList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: CountriesListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.countries_list_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemBinding.country = mList!![position]
    }

    class ViewHolder(var itemBinding: CountriesListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root), LayoutContainer {
        override val containerView: View?
            get() = itemBinding.root
    }
}
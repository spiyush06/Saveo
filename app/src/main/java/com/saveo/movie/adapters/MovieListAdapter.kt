package com.saveo.movie.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.saveo.movie.R
import com.saveo.movie.databinding.MovieListItemBinding
import com.saveo.movie.models.MovieList
import com.saveo.movie.ui.DetailsActivity
import kotlinx.android.extensions.LayoutContainer

class MovieListAdapter : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    private var mList: List<MovieList>? = listOf()

    fun setData(list: List<MovieList>) {
        mList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: MovieListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.movie_list_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = mList!![position]
        holder.itemBinding.country = mList!![position]
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailsActivity::class.java)
            intent.putExtra("description", list.overview)
            intent.putExtra("title", list.title)
            intent.putExtra("release_date", list.release_date)
            intent.putExtra("rating", list.rating)
            intent.putExtra("image_url", list.poster)
            intent.putExtra("genres_1", list.genres_1)
            intent.putExtra("genres_2", list.genres_2)
            intent.putExtra("genres_3", list.genres_3)
            intent.putExtra("cities", list.cities)
            intent.putExtra("users", list.users)
            intent.putExtra("hour", list.hour)
            holder.itemView.context.startActivity(intent)
        }
    }

    class ViewHolder(var itemBinding: MovieListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root), LayoutContainer {
        override val containerView: View?
            get() = itemBinding.root
    }
}
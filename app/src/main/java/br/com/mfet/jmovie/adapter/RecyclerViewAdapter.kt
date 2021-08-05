package br.com.mfet.jmovie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import br.com.mfet.jmovie.R
import br.com.mfet.jmovie.databinding.MovieItemBinding
import br.com.mfet.jmovie.models.RecyclerData
import com.bumptech.glide.Glide

//class MyViewHolder(val binding: MovieItemBinding)

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    var items = ArrayList<RecyclerData>()

    fun setUpdateData(items: ArrayList<RecyclerData>) {
        this.items = items
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: MovieItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater, parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
//        val itemUpComing = moviesUpComing[position]

        holder.let {

            it.binding.title = item.title

            Glide.with(it.binding.root)
                .load("https://image.tmdb.org/t/p/w500${item.poster_path}")
                .into(it.binding.ivPoster)
        }
    }

    override fun getItemCount() = items.size

}
package exronin.mycinemov.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import exronin.mycinemov.core.R
import exronin.mycinemov.core.databinding.MoviesItemBinding
import exronin.mycinemov.core.domain.model.Mov
import java.util.*

class MovAdapter : RecyclerView.Adapter<MovAdapter.ListViewHolder>() {

    private var listData = ArrayList<Mov>()
    var onItemClick: ((Mov) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newListData: List<Mov>?) {
        if (newListData == null) return
        val diffCallback = MovDiffCallback(listData, newListData)
         diffCallback.newListSize
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movies_item, parent, false)
        )

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = MoviesItemBinding.bind(itemView)
        fun bind(data: Mov) {
            with(binding) {
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w500${data.poster}")
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(R.drawable.no_poster)
                    .into(posterMovie)
                tvOverview.text = data.overview
                tvTitle.text = data.name
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}
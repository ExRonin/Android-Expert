package exronin.mycinemov.ui.search.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import exronin.mycinemov.R
import exronin.mycinemov.core.domain.model.Mov
import exronin.mycinemov.databinding.ItemSearchBinding

class SearchAdapter(private val ignoredOnItemClick: (Mov) -> Unit) : ListAdapter<Mov, SearchAdapter.SearchViewHolder>(
    DiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchBinding.inflate(inflater, parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            ignoredOnItemClick(movie)
        }
    }

    class SearchViewHolder(private val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Mov) {
            binding.titlesearch.text = movie.name
            binding.ratingTv.text = movie.vote_average?.toString() ?: ""
            Glide.with(binding.root)
                .load("https://image.tmdb.org/t/p/w500/${movie.poster}")
                .placeholder(R.drawable.no_poster)
                .error(R.drawable.no_poster)
                .into(binding.imageView)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Mov>() {
        override fun areItemsTheSame(oldItem: Mov, newItem: Mov): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Mov, newItem: Mov): Boolean {
            return oldItem == newItem
        }
    }
}

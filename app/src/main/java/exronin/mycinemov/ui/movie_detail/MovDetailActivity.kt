package exronin.mycinemov.ui.movie_detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import exronin.mycinemov.R
import exronin.mycinemov.core.domain.model.Mov
import exronin.mycinemov.databinding.ActivityMovieDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private val detailViewModel: MovDetailViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        @Suppress("DEPRECATION") val detailMovie = intent.getParcelableExtra<Mov>(EXTRA_DATA)
        showDetailMovie(detailMovie)


    }

    private fun shareSetting(name: String, overview: String?) {
        val mimeType = "text/plain"
        @Suppress("DEPRECATION")
        ShareCompat.IntentBuilder.from(this).apply {
            setType(mimeType)
            setChooserTitle(name)
            setText("$name \n $overview")
            startChooser()
        }
    }

    private fun showDetailMovie(detailMovie: Mov?) {
        detailMovie?.let {
            supportActionBar?.title = detailMovie.name
            binding.contentDetailMovie.apply {
                tvMovieDetailTitle.text = detailMovie.name
                tvTittle.text = detailMovie.name
                tvOverviewDetail.text = detailMovie.overview
                tvRatingMovie.text = detailMovie.vote_average.toString()
                tvInformationDetail.text = detailMovie.original_language
                Glide.with(this@MovDetailActivity)
                    .load("https://image.tmdb.org/t/p/w500${detailMovie.poster}")
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(R.drawable.ic_baseline_local_movies_24)
                    .into(posterMovie)
                var statusFavorite = detailMovie.isFavorite
                setStatusFavorite(statusFavorite)

                addToFavorite.setOnClickListener {
                    statusFavorite = !statusFavorite
                    detailViewModel.setFavoriteMovie(detailMovie, statusFavorite)
                    setStatusFavorite(statusFavorite)
                    showSnackBar(statusFavorite, detailMovie.name)
                }

                buttonShare.setOnClickListener {
                    shareSetting(detailMovie.name, detailMovie.overview)
                }

                backIv.setOnClickListener {
                    @Suppress("DEPRECATION")
                    onBackPressed()
                }

            }

        }
    }

    private fun showSnackBar(state: Boolean, name: String) {
        val msg = if (state) {
            "$name " + getString(R.string.added_to_favorite)
        } else {
            "$name " + getString(R.string.removed_from_favorite)
        }
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG)
            .show()
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.contentDetailMovie.addToFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_bookmark_24
                )
            )

        } else {
            binding.contentDetailMovie.addToFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_bookmark_border_24
                )
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        @Suppress("DEPRECATION")
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_DATA = "movie"
    }
}
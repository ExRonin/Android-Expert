package exronin.mycinemov.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import exronin.mycinemov.R
import dagger.hilt.android.AndroidEntryPoint
import exronin.mycinemov.databinding.ActivityMainBinding


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var host: NavHostFragment
    private lateinit var graph: NavGraph
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        host = supportFragmentManager.findFragmentById(R.id.mainNavHostFragment) as NavHostFragment
        graph = host.navController.navInflater.inflate(R.navigation.nav_graph)
        graph.setStartDestination(R.id.navigation_movie)

        navController = host.navController

        val navView: BottomNavigationView = binding.navView
        NavigationUI.setupWithNavController(navView, navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.searchFragment) {
                supportActionBar?.show()
                navView.visibility = View.GONE
            } else {
                supportActionBar?.hide()
                navView.visibility = View.VISIBLE
            }
        }
    }


    override fun onResume() {
        super.onResume()
        graph.setStartDestination(R.id.navigation_movie)
        host.navController.graph = graph
    }
}

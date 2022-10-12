package be.bf.android.recetteapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import be.bf.android.recetteapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {


    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(bottomNav, navController)

        setupActionBarWithNavController(findNavController(R.id.nav_host_fragment))

        navController.addOnDestinationChangedListener {_, destination, _ ->
            if((destination.id == R.id.loginFragment) || (destination.id == R.id.registerFragment)){
                bottomNav.visibility = View.GONE
            } else {
                bottomNav.visibility = View.VISIBLE
            }
        }




    }



    override fun onSupportNavigateUp(): Boolean {
        val navControl = findNavController(R.id.nav_host_fragment)
        return navControl.navigateUp() || super.onSupportNavigateUp()
    }


    override fun onBackPressed() {
        super.onBackPressed()
    }


}
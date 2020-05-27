package com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.CommunityApp
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.R

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //start workers
        CommunityApp.instance.startWorkers()

        val navController =
            Navigation.findNavController(this@MainActivity, R.id.navHostFragment)

        NavigationUI.setupActionBarWithNavController(this@MainActivity, navController)
        appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController =
            Navigation.findNavController(this@MainActivity, R.id.navHostFragment)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}

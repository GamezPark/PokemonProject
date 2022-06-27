package com.pokemon.project.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.pokemon.project.R
import com.pokemon.project.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        startDestination(R.id.homeFragment, null)
    }

    private fun startDestination(destination: Int, arguments: Bundle?){
        val navController = this.findNavController(R.id.nav_host_home)
        val navHostFragment = nav_host_home as NavHostFragment
        val graphInflater = navHostFragment.navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.nav_pokemon_project)
        navGraph.startDestination = destination
        navController.setGraph(navGraph, arguments)
        updateNavigation()
    }


    private fun updateNavigation (){
        setSupportActionBar(binding.toolbar)
        val navController = this.findNavController(R.id.nav_host_home)
        NavigationUI.setupWithNavController(binding.toolbar, navController)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }
}
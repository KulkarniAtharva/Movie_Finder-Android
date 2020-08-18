package dev.atharvakulkarni.moviefinder.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.atharvakulkarni.moviefinder.R

class HomeActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home);

        setupViewModel()
    }

    private fun setupViewModel()
    {

    }
}
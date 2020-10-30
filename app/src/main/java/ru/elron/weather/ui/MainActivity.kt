package ru.elron.weather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.elron.weather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}

package ru.elron.weather.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.elron.weather.databinding.ActivityMainBinding
import ru.elron.weather.interfaces.ToolBarable

class MainActivity : AppCompatActivity(), ToolBarable {
    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun setTitle(text: String?) {
        binding.toolbar.title = text
    }

    override fun setSubtitle(text: String?) {
        binding.toolbar.subtitle = text
    }
}

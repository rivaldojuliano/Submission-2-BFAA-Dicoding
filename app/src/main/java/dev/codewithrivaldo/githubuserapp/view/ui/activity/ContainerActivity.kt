package dev.codewithrivaldo.githubuserapp.view.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.codewithrivaldo.githubuserapp.databinding.ActivityContainerBinding

class ContainerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContainerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
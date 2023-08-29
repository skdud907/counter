package com.example.counter

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.example.counter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()

    lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.counterText.text = "${viewModel.count}"

        viewModel.countLiveData.observe(this) {
            binding.counterText.text = "$it"
        }

        binding.addButton.setOnClickListener {
            viewModel.increaseCount()
        }

        binding.minusButton.setOnClickListener {
            viewModel.decreaseCount()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("count", viewModel.count)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        viewModel.count = savedInstanceState.getInt("count")
        Log.d(TAG, "onRestoreInstanceState: ${viewModel.count}")
    }

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }
}
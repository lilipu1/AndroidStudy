package edu.frank.androidStudy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import edu.frank.androidStudy.databinding.ActivityMainBinding
import edu.frank.androidStudy.extension.setBindingLayout

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: SubjectsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = setBindingLayout<ActivityMainBinding>(this, R.layout.activity_main)
        setSupportActionBar(binding.mToolbar)
        binding.mToolbar.apply {
            title = "安卓学习"
        }

        adapter = SubjectsAdapter()
        adapter.items = arrayListOf("请求与条目绑定")
        adapter.itemClickListener = {position ->
            when (position) {
                0 -> {
                    startActivity(Intent(this,ListRequestActivity::class.java))
                }
                else -> {
                }
            }
        }

        binding.rvSubjects.adapter = adapter

        binding.progress.progress = 40
        binding.progress.setOnClickListener {
            if (binding.progress.progress == -2){
                binding.progress.progress = 40
            }else{
                binding.progress.progress = -2
            }
        }
    }
}
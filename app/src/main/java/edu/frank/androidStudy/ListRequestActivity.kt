package edu.frank.androidStudy

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import edu.frank.androidStudy.databinding.ActivityListRequestBinding
import edu.frank.androidStudy.extension.setBindingLayout
import javax.inject.Inject

@AndroidEntryPoint
class ListRequestActivity : AppCompatActivity() {

    @Inject
    lateinit var adapter: SubjectsAdapter
    lateinit var viewModel: ListRequestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ListRequestViewModel::class.java)
        val binding =
            setBindingLayout<ActivityListRequestBinding>(this, R.layout.activity_list_request)

        val items = arrayListOf<String>()
        for (i in 0..1000) {
            items.add(i.toString())
        }

        adapter.items = items
        adapter.itemClickListener = { position ->
            viewModel.updateStatus(position)
        }
        viewModel.status.observe(this, Observer {
            Log.e("activity",it.data.toString())
        })
        binding.rv.adapter = adapter

    }
}
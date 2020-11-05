package edu.frank.androidStudy

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import edu.frank.androidStudy.databinding.ActivityListRequestBinding
import edu.frank.androidStudy.extension.setBindingLayout
import edu.frank.androidStudy.http.Resource
import edu.frank.androidStudy.http.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ListRequestActivity : AppCompatActivity() {

    @Inject
    lateinit var adapter: SubjectsAdapter

    @Inject
    lateinit var service: ApiService

    private val viewModel: ListRequestViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            setBindingLayout<ActivityListRequestBinding>(this, R.layout.activity_list_request)

        val items = arrayListOf<String>()
        for (i in 0..1000) {
            items.add(i.toString())
        }

        adapter.items = items
        adapter.itemClickListener = { position ->
            viewModel.updateStatus(position)

            viewModel.status.observe(this, Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        if (it.message.toIntOrNull() == position){
                            adapter.addClickedPosition(position)
                            adapter.notifyItemChanged(position)
                        }
                    }
                }
            })
            /* CoroutineScope(Dispatchers.IO).launch {
                 val response = service.updateStatus(position)
                 Timber.tag("响应:").e(response.to)
                 CoroutineScope(Dispatchers.Main).launch {
                     if (response.code == 200 && response.data == "true") {
                         adapter.addClickedPosition(position)
                         adapter.notifyItemChanged(position)
                     }
                 }
             }*/


        }

        binding.rv.adapter = adapter

    }

}
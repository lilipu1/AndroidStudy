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
            val viewModel  = ListRequestViewModel(ListRequestModel(service))
            viewModel.updateStatus(position)
            viewModel.status.observe(this, Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        adapter.addClickedPosition(position)
                        adapter.notifyItemChanged(position)
                    }
                }
            })
        }

        binding.rv.adapter = adapter

    }

}
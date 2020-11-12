package edu.frank.androidStudy

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListRequestViewModel @ViewModelInject constructor(
    private val model: ListRequestModel
) :
    ViewModel() {
    private val para = MutableLiveData<Int>()

    val status = Transformations.switchMap(para) {
        model.updateStatus(it)
    }

    fun updateStatus(position: Int) {
        para.value = position
    }
}
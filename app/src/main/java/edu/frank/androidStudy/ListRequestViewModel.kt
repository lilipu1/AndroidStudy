package edu.frank.androidStudy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class ListRequestViewModel @Inject constructor(private val model: ListRequestModel) : ViewModel() {
    private val para = MutableLiveData<Int>()

    val status = Transformations.map(para) {
        model.updateStatus(it)
    }

    fun updateStatus(position: Int) {
        para.value = position
    }

}
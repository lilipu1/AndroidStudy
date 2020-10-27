package edu.frank.androidStudy.extension

import android.app.Activity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

fun <T : ViewDataBinding> Activity.setBindingLayout(activity: Activity, layoutId: Int): T {
    return DataBindingUtil.setContentView<T>(activity, layoutId)
}
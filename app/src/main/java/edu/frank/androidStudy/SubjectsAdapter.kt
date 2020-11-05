package edu.frank.androidStudy

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

class SubjectsAdapter @Inject constructor() :
    RecyclerView.Adapter<SubjectsAdapter.ViewHolder>() {

    lateinit var items: ArrayList<String>
    val clickedList: ArrayList<Int> = arrayListOf()

    lateinit var itemClickListener: (position: Int) -> Unit

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSubject = itemView.findViewById<TextView>(R.id.tvSubject)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_subject, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvSubject.text = items[position]
        if (clickedList.contains(position)) {
            holder.itemView.setBackgroundColor(Color.parseColor("#ff0000"))
        }else{
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))
        }
        holder.itemView.setOnClickListener {
            itemClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addClickedPosition(position: Int) {
        clickedList.add(position)
    }
}
package com.towhid.tasknote.ac_main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.towhid.tasknote.R
import com.towhid.tasknote.ac_main.model.Task
import com.towhid.tasknote.ac_main.view.MainActivity
import kotlinx.android.synthetic.main.item_task.view.*

class RecylerAdapterTask(
    var mainActivity: MainActivity,
    var context: Context,
    var mData: MutableList<Task>
) :
    RecyclerView.Adapter<RecylerAdapterTask.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.item_task, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.task_details.text = mData[position].taskName

        if (mData[position].isEmergency)
            holder.itemView.tags.setBackgroundResource(
                R.color.purple_200
            )
        else holder.itemView.tags.setBackgroundResource(
            R.color.colorGreen
        )
       // holder.product.setOnClickListener { mainActivity.onItemClick(mData[position]) }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}





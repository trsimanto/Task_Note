package com.towhid.tasknote.ac_main.view

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.towhid.tasknote.R
import com.towhid.tasknote.ac_main.adapter.RecylerAdapterTask
import com.towhid.tasknote.ac_main.listener.TaskClickListener
import com.towhid.tasknote.ac_main.model.Task
import com.towhid.tasknote.ac_main.viewModel.TaskViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TaskClickListener {


    lateinit var taskViewModel: TaskViewModel
    var taskList = mutableListOf<Task>()
    private lateinit var recylerAdapterTask: RecylerAdapterTask

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        action()
    }

    private fun action() {
        viewAllTask()
        add.setOnClickListener {
            val dialog = Dialog(this@MainActivity)
            dialog.setContentView(R.layout.dialog_add_update_product)
            dialog.create()
            dialog.findViewById<View>(R.id.add_button).setOnClickListener {
                val productName = dialog.findViewById<EditText>(R.id.product_name)
                val emergency = dialog.findViewById<CheckBox>(R.id.emergency_button)
                taskViewModel.insertTask(
                    Task(
                        taskName = productName.text.toString(),
                        isEmergency = emergency.isChecked
                    )
                )
                dialog.dismiss()
            }
            dialog.show()
        }
        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteProduct(viewHolder.adapterPosition)
            }
        }).attachToRecyclerView(rec_task)

    }

    private fun init(){
        taskViewModel =
            ViewModelProvider(this)[TaskViewModel::class.java]
        recylerAdapterTask = RecylerAdapterTask(this, this, taskList)
        rec_task.layoutManager = LinearLayoutManager(this)
        rec_task.adapter = recylerAdapterTask

 }
    private fun deleteProduct(position: Int) {
        taskViewModel.deleteTask(taskList[position])
    }
    private fun viewAllTask() {
        taskViewModel.allTaskList.observe(this@MainActivity) { products ->
            if (products.isEmpty()) empty_text!!.visibility =
                View.VISIBLE else empty_text!!.visibility =
                View.GONE
            taskList.clear()
            taskList.addAll(products)
            recylerAdapterTask.notifyDataSetChanged()
        }
    }
    override fun onItemClick(product: Task) {
        val dialog = Dialog(this@MainActivity)
        dialog.setContentView(R.layout.dialog_add_update_product)
        dialog.create()
        val productName = dialog.findViewById<EditText>(R.id.product_name)
        val emergency = dialog.findViewById<CheckBox>(R.id.emergency_button)
        val update_button = dialog.findViewById<TextView>(R.id.add_button)
        productName.setText(product.taskName)
        update_button.text = "Update"
        emergency.isChecked = product.isEmergency
        update_button.setOnClickListener {
            product.taskName = productName.text.toString()
            product.isEmergency = emergency.isChecked
            taskViewModel.updateTask(product)
            dialog.dismiss()
        }
        dialog.show()
    }

}
package com.towhid.tasknote.database

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.towhid.tasknote.ac_main.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class Repository(application: Application?, override val coroutineContext: CoroutineContext) :
    CoroutineScope {
    private val taskDao: TaskDao
    private lateinit var allProduct: LiveData<MutableList<Task>>

    fun getAllTask(): LiveData<MutableList<Task>> {
        return allProduct
    }

    fun insertTask(task: Task) {
        launch {
            taskDao.insectTask(task)
        }
    }

    fun updateTask(task: Task) {
        launch {
            taskDao.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        launch {
            taskDao.deleteTask(task)
        }
    }

    init {
        val db: TaskDatabase = TaskDatabase.invoke(application as Context)
        taskDao = db.getTaskDao()
        launch {
            allProduct = taskDao.getAllTask()
        }

    }


}

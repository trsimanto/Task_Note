package com.towhid.tasknote.database

import androidx.lifecycle.LiveData
import com.towhid.tasknote.ac_main.model.Task

class Repository(private val taskDao: TaskDao) {
    val readAllTask: LiveData<List<Task>> = taskDao.getAllTask()

    fun insertTask(task: Task) {
        taskDao.insectTask(task)
    }

    fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }
}

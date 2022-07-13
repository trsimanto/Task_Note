package com.towhid.tasknote.ac_main.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.towhid.tasknote.ac_main.model.Task
import com.towhid.tasknote.database.Repository
import com.towhid.tasknote.database.TaskDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application): AndroidViewModel(application) {
    val allTaskList: LiveData<List<Task>>
    private val repository: Repository

    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository= Repository(taskDao)
        allTaskList = repository.readAllTask
    }

    fun insertTask(user: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTask(user)
        }
    }

    fun updateTask(user: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(user)
        }
    }

    fun deleteTask(user: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(user)
        }
    }

}

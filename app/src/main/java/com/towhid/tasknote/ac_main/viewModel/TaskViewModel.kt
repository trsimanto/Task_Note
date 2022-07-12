package com.towhid.tasknote.ac_main.viewModel

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.towhid.tasknote.ac_main.model.Task
import com.towhid.tasknote.database.Repository
import kotlin.coroutines.CoroutineContext

class TaskViewModel(application: Application , coroutineContext: CoroutineContext) :
    AndroidViewModel(application) {
    private val repository: Repository
    private val allProduct: LiveData<MutableList<Task>>
    val allProdects: LiveData<MutableList<Task>>
        get() = allProduct

    fun insertTask(task: Task) {
        repository.insertTask(task)
    }

    fun updateTask(task: Task) {
        repository.updateTask(task)
    }

    fun deleteTask(task: Task) {
        repository.deleteTask(task)
    }

    init {
        repository = Repository(application,coroutineContext)
        allProduct = repository.getAllTask()
    }


}

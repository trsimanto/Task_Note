package com.towhid.tasknote.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.towhid.tasknote.ac_main.model.Task

@Dao
interface TaskDao {
    @Insert
    fun insectTask(product: Task?)

    @Update
    fun updateTask(product: Task?)

    @Delete
    fun deleteTask(product: Task?)

    @Query("Select * From Task Order By id DESC")
    fun getAllTask(): LiveData<MutableList<Task?>?>?

}
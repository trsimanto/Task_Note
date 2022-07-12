package com.towhid.tasknote.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.towhid.tasknote.ac_main.model.Task

@Dao
interface TaskDao {
    @Insert
    suspend fun insectTask(product: Task?)

    @Update
    suspend fun updateTask(product: Task?)

    @Delete
    suspend fun deleteTask(product: Task?)

    @Query("Select * From Task Order By id DESC")
    suspend fun getAllTask(): LiveData<MutableList<Task?>?>?

}
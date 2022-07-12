package com.towhid.tasknote.ac_main.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var taskName: String? = null,
    var isEmergency: Boolean = false)

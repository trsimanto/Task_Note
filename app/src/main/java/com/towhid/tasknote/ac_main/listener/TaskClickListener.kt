package com.towhid.tasknote.ac_main.listener

import com.towhid.tasknote.ac_main.model.Task

interface TaskClickListener {
    fun onItemClick(product: Task)
}
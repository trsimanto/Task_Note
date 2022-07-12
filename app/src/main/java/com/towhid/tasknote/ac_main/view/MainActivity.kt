package com.towhid.tasknote.ac_main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.towhid.tasknote.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
        setContentView(R.layout.activity_main)

    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
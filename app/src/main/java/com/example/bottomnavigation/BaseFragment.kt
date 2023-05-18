package com.example.bottomnavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

//extends the Fragment class and implements the CoroutineScope interface
//coroutinescope is to perform tasks that could take a significant amount of time to complete
//- performing tasks that may take a long time to complete, such as  database operations
//- like kotlin assistant to assign the jobs when to start and end to avoid time consuming and traffic jam
abstract class BaseFragment : Fragment(),CoroutineScope{

    //lateinit modifier is used here to indicate that this property will be initialized later, rather than in the constructor.
    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
    //indicates that coroutines launched by this scope will run on the main thread.
        get() = job +Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // initializes the job object with a new instance of Job.
        job = Job()
    }

    override fun onDestroy() {
        super.onDestroy()
        // cancels the job object, which will cancel any coroutines launched by this scope.
        job.cancel()
    }
}
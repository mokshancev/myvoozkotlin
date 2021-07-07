package com.example.homelibrary.helpers

import android.os.Handler
import android.os.Message
import android.os.SystemClock
import java.lang.Exception
import java.util.concurrent.CountDownLatch
import kotlin.properties.Delegates

class DispatchQueue: Thread {
    private lateinit var handler: Handler
    private val syncLatch = CountDownLatch(1)
    private var lastTaskTime by Delegates.notNull<Long>()

    constructor(threadName: String){
        DispatchQueue(threadName, true)
    }

    constructor(threadName: String, start: Boolean){
        name = threadName
        if(start){
            start()
        }
    }

    fun sendMessage(msg: Message, delay: Long){
        try{
            syncLatch.await()
            if(delay <= 0){
                handler.sendMessage(msg)
            }
            else{
                handler.sendMessageDelayed(msg, delay)
            }
        }
        catch (ignore: Exception){ }
    }

    fun cancelRunnable(runnable: Runnable){
        try{
            syncLatch.await()
            handler.removeCallbacks(runnable)
        }
        catch (e: Exception){
            //todo utils printerror (e.getmessage, e)
        }
    }

    fun cancelRunnables(runnables : Array<Runnable>){
        try{
            syncLatch.await()
            for (runnable in runnables){
                handler.removeCallbacks(runnable)
            }
        }
        catch (e: Exception){
            //todo utils printerror (e.getmessage, e)
        }
    }

    fun postRunnable(runnable: Runnable): Boolean{
        lastTaskTime = SystemClock.elapsedRealtime()
        return postRunnable(runnable, 0)
    }

    fun postRunnable(runnable: Runnable, delay: Long): Boolean{
        try{
            syncLatch.await()
        }
        catch (e: Exception){
            //todo utils printerror (e.getmessage, e)
        }
        if(delay <= 0){
            return handler.post(runnable)
        }
        else{
            return handler.postDelayed(runnable, delay)
        }
    }
}
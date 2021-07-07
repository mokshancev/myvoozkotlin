package com.example.homelibrary

class HomeManager {
    lateinit var lessonClass: ScheduleClass

    companion object{
        private lateinit var instance: HomeManager
        private lateinit var networkHelper: Netwo

        fun get(): HomeManager{
            if(instance == null){
                synchronized(HomeManager.javaClass){
                    if(instance == null){
                        instance = HomeManager()
                    }
                }
            }
            return instance
        }
    }

    fun withSchedule(): ScheduleClass{
        if(lessonClass == null){
            lessonClass = ScheduleClass()
        }
        return lessonClass
    }

    class ScheduleClass {
        fun loadLessons(): MutableList<Less>
    }
}

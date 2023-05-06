package com.dzdexon.memomartian

import android.app.Application
import com.dzdexon.memomartian.data.AppContainer
import com.dzdexon.memomartian.data.AppDataContainer

class NotesApplication: Application() {
    /*
    * App container instance used by the rest of classes to obtain dependencies
    * */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(context = this)
    }
}
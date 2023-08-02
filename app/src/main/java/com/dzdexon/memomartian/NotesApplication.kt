package com.dzdexon.memomartian

import android.app.Application
import android.content.Intent
import android.net.Uri
import com.dzdexon.memomartian.data.AppContainer
import com.dzdexon.memomartian.data.AppDataContainer

class NotesApplication : Application() {
    /*
    * App container instance used by the rest of classes to obtain dependencies
    * */
    lateinit var container: AppContainer

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(context = this)
    }

    companion object {
        private var instance: NotesApplication? = null
        fun getUriPermission(uri: Uri) {
            instance!!.applicationContext.contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }
    }

}
package co.andsomore.notin

import android.app.Application
import co.andsomore.notin.di.appModule
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class NoteApp: Application(){
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@NoteApp)
            modules(appModule) // Your Koin module
        }
    }

}
package arief.com.sampletest

import android.app.Application
import android.arch.persistence.room.Room
import android.util.Log
import arief.com.sampletest.model.AppDatabase
import arief.com.sampletest.model.User
import com.facebook.stetho.Stetho
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors

class MyApplication : Application() {

    val appDatabase by lazy {

        Room.databaseBuilder(this, AppDatabase::class.java, "buku-db").build()
    }

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)


        appDatabase.userDao().isNotAnyUser()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer {
                    Log.d("AF", " ini db "+it)
                    if(it == 0){
                        val executor = Executors.newSingleThreadExecutor()
                        executor.execute(Runnable {

                            val users = ArrayList<User>()
                            users.add(User("jhony", "12345", "jhony", false))
                            users.add(User("akbar", "12345", "akbar", false))
                            users.add(User("mark", "12345", "mark", false))
                            users.add(User("marques", "12345", "marques", false))
                            users.add(User("admin", "admin", "marques", true))
                            appDatabase.userDao().insertAll(
                                    users
                            )

                        })


                    }

                })

       }

}
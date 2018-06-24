package arief.com.sampletest.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [User::class, Buku::class], version = 3)
abstract class AppDatabase : RoomDatabase(){
    abstract fun userDao() : DaoUser
    abstract fun bukuDao() : BukuDao
}
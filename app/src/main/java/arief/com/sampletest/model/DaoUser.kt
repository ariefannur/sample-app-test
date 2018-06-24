package arief.com.sampletest.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import io.reactivex.Flowable;


@Dao
interface DaoUser {

    @Query("SELECT * FROM user where username = :username and password = :password")
    fun login(username:String, password:String): Flowable<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users:List<User>)

    @Query("SELECT count(*) FROM user")
    fun isNotAnyUser():Flowable<Int>

}
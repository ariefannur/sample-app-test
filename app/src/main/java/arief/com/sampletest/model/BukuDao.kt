package arief.com.sampletest.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface BukuDao {

    @Insert
    fun inserBuku(buku: Buku)

    @Query("SELECT * from buku")
    fun getBuku():LiveData<List<Buku>>
}
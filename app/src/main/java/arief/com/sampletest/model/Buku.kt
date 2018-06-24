package arief.com.sampletest.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Buku(@PrimaryKey(autoGenerate = true) val id:Int, val name:String, val qty:Int)

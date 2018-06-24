package arief.com.sampletest.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class User (@PrimaryKey val username:String, val password:String, val name:String, val is_admin:Boolean)

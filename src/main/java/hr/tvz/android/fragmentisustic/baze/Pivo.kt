package hr.tvz.android.fragmentisustic.baze

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pivo(
    @PrimaryKey(autoGenerate = false) val id: Int?,
    @ColumnInfo val naslov:String,
    @ColumnInfo val detalji:String,
    @ColumnInfo val slika: String,
    @ColumnInfo val link:String,
    @ColumnInfo val tema: Int
)
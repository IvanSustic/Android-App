package hr.tvz.android.fragmentisustic.baze

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Pivo::class], version = 2)
abstract class BazaPodataka : RoomDatabase() {
    abstract fun pivoDao(): PivoDAO
}
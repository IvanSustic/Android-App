package hr.tvz.android.fragmentisustic.baze

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PivoDAO {

    @Query("SELECT * FROM Pivo ORDER BY id ASC")
    fun getAll(): MutableList<Pivo>

    @Query("SELECT * FROM Pivo WHERE id=:id")
    fun getById(id:Int): Pivo

    @Insert
    fun insertOne(todo: Pivo)


}
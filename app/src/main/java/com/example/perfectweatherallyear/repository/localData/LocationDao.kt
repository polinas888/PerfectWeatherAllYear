package com.example.perfectweatherallyear.repository.localData

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.perfectweatherallyear.model.Location
import io.reactivex.rxjava3.core.Flowable

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLocation(locations: List<Location>)

    @Query("SELECT location.id FROM location WHERE name =:city")
    fun getLocationIdByCityName(city: String): Flowable<Int>

    @Query("SELECT location.name FROM location WHERE location.id =:cityId")
    suspend fun getCityNameByCityId(cityId: Int): String

    @Query("SELECT * FROM location")
    suspend fun getLocations(): List<Location>
}
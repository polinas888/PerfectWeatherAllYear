package com.example.perfectweatherallyear.repository.localData

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.perfectweatherallyear.model.Location
import io.reactivex.Observable

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLocations(locations: List<Location>)

    @Query("SELECT location.id FROM location WHERE name =:city")
    fun getLocationIdByCityName(city: String): Observable<Int>

    @Query("SELECT location.name FROM location WHERE location.id =:cityId")
    fun getCityNameByCityId(cityId: Int): Observable<String>

    @Query("SELECT * FROM location")
    fun getLocations(): Observable<List<Location>?>
}
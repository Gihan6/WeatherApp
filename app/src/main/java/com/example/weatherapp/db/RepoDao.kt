package com.example.weatherapp.db

import androidx.room.*

@Dao

interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg repos: Repo)

    @Query("select * from repo ")
    fun getCities(): List<Repo>

    @Query("DELETE FROM Repo")
    fun clear()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(vararg history: History)

    @Query("select * from History where cityId= :id ")
    fun getHistory(id: Int): List<History>
}
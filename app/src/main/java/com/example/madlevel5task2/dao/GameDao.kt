package com.example.madlevel5task2.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.madlevel5task2.model.Game

@Dao
interface GameDao {

    @Insert
    fun saveGame(reminder: Game)

    @Query("SELECT * FROM GameTable")
    fun getGames(): LiveData<List<Game>>

    @Query("DELETE FROM GameTable")
    fun deleteAll()

    @Delete
    fun deleteGame(game: Game)

}

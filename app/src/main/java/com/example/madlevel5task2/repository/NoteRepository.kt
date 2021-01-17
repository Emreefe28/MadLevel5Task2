package com.example.madlevel5task2.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.madlevel5task2.dao.GameDao
import com.example.madlevel5task2.database.GamesRoomDatabase
import com.example.madlevel5task2.model.Game

class NoteRepository(context: Context) {

    private val gameDao: GameDao

    init {
        val database = GamesRoomDatabase.getDatabase(context)
        gameDao = database!!.noteDao()
    }

    fun getGames(): LiveData<List<Game>> {
        return gameDao.getGames()
    }

    fun deleteGame(game: Game) {
        gameDao.deleteGame(game)
    }

    fun deleteAllGames() {
        gameDao.deleteAll()
    }

    fun saveGame(game: Game) {
        gameDao.saveGame(game)
    }

}

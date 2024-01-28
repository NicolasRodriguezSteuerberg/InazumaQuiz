package com.example.myapplication.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.models.Ranking

@Dao
interface RankingDao {
    @Query("SELECT * FROM ranking ORDER BY score DESC LIMIT 10")
    fun getRanking(): List<Ranking>

    @Insert
    suspend fun insertRanking(ranking: Ranking)
}
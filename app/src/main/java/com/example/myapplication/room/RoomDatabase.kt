package com.example.myapplication.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.models.Questions
import com.example.myapplication.models.Ranking

@Database(
    entities = [Questions::class, Ranking::class],
    version = 1,
    exportSchema = false
)
abstract class RoomDatabase: RoomDatabase() {
    abstract fun QuestionsDao():QuestionsDao
    abstract fun RankingDao():RankingDao
}
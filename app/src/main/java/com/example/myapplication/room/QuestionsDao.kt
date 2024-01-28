package com.example.myapplication.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.models.Questions

@Dao
interface QuestionsDao {

    @Query("SELECT * FROM questions ORDER BY RANDOM() LIMIT 10")
    fun getQuestions(): List<Questions>

    @Insert
    suspend fun insertQuestion(questions: List<Questions>)

    @Insert
    suspend fun insertQuestion(questions: Questions)
}
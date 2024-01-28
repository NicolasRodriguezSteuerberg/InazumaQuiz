package com.example.myapplication.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class Questions(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    @ColumnInfo(name = "question_type")
    val question: String,
    @ColumnInfo(name = "correct")
    val correct: String,
    @ColumnInfo(name = "wrong1")
    val wrong1: String,
    @ColumnInfo(name = "wrong2")
    val wrong2: String
)

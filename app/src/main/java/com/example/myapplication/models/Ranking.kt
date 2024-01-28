package com.example.myapplication.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ranking")
data class Ranking (
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    @ColumnInfo(name = "user")
    val user: String,
    @ColumnInfo(name = "score")
    val score: Int
)
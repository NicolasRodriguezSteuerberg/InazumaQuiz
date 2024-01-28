package com.example.myapplication.models

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

object RankingData{
    var score = mutableStateOf(0)
    var rankingList = listOf<Ranking>()
    var nameTxt = mutableStateOf("")
}

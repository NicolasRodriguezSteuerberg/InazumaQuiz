package com.example.myapplication.states

import com.example.myapplication.models.Questions

data class QuestionsState (
    val questionsList: List<Questions> = emptyList()
)

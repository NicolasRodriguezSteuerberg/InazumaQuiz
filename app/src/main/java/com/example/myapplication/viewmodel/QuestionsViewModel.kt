package com.example.myapplication.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.Questions
import com.example.myapplication.models.QuestionsData
import com.example.myapplication.models.RankingData
import com.example.myapplication.room.QuestionsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuestionsViewModel(private val questionsDao: QuestionsDao): ViewModel() {

    fun getQuestions() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                QuestionsData.questionsToShow = questionsDao.getQuestions()
                if (QuestionsData.questionsToShow.isEmpty()) {
                    questionsDao.insertQuestion(QuestionsData.addQuestions)
                    getQuestions()
                }
                Log.d("ELEMENTS", QuestionsData.questionsToShow.toString())
            }
        }
    }

    fun isCorrectAnswer(answer: String) {
        if (answer == QuestionsData.correctAnswer) {
            RankingData.score.value += 1
            QuestionsData.roundQuestion.value += 1
        }
    }

    fun addQuestion(question: Questions) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                questionsDao.insertQuestion(question)
            }
        }
    }


}
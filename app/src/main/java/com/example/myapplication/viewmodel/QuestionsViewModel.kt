package com.example.myapplication.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.Questions
import com.example.myapplication.models.QuestionsData
import com.example.myapplication.models.RankingData
import com.example.myapplication.room.QuestionsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuestionsViewModel(private val questionsDao: QuestionsDao): ViewModel() {

    fun getQuestions() {
        viewModelScope.launch {
            // need to use Dispatchers.IO to get data from database in background thread(if not, app will crash)
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

    fun decreaseCounter() {
        viewModelScope.launch {
            if(QuestionsData.counterActive.value && QuestionsData.counterTime.value > 0){
                delay(1000)
                QuestionsData.counterTime.value -= 1
            }
        }
    }

    fun isCorrectAnswer(answer: String) {
        if (answer == QuestionsData.correctAnswer) {
            RankingData.score.value += 1
        }
        else {
            RankingData.score.value -= 1
        }
        QuestionsData.roundQuestion.value += 1
        QuestionsData.counterTime.value = 30
    }

    fun addQuestion(question: Questions) {
        viewModelScope.launch {
            // need to use Dispatchers.IO to insert in database in background thread(if not, app will crash)
            withContext(Dispatchers.IO) {
                questionsDao.insertQuestion(question)
            }
        }
    }


}
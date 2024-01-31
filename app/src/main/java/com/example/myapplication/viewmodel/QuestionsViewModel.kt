package com.example.myapplication.viewmodel


import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.Data
import com.example.myapplication.models.Questions
import com.example.myapplication.models.QuestionsData
import com.example.myapplication.models.RankingData
import com.example.myapplication.room.QuestionsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuestionsViewModel(private val questionsDao: QuestionsDao): ViewModel() {

    private var timer: CountDownTimer? = null

    fun startTimer() {
        Data.counter.value = 31
        timer = object : CountDownTimer(Data.counter.value.toLong() * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Data.counter.value--
            }

            override fun onFinish() {
                // Lógica para manejar el tiempo agotado
                viewModelScope.launch{
                    QuestionsData.buttonState.value = "timeOut"
                    delay(2000)
                    QuestionsData.buttonState.value = "none"
                    QuestionsData.roundQuestion.value += 1
                    Data.buttonEnabled.value = true
                    if (QuestionsData.roundQuestion.value < QuestionsData.questionsToShow.size) {
                        startTimer()
                    }
                }
            }
        }.start()
    }

    fun resetTimer() {
        timer?.cancel()
    }

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


    // pasarle a lo mejor el boton?
    fun isCorrectAnswer(answer: String, clicked: MutableState<Boolean>) {
        // parar el tiempo
        resetTimer()
        if (answer == QuestionsData.correctAnswer) {
            RankingData.score.value += 1
            QuestionsData.buttonState.value = "correct"
        }
        else {
            RankingData.score.value -= 1
            QuestionsData.buttonState.value = "wrong"
        }
        viewModelScope.launch {
            // añadir el borde a la respuesta seleccionada
            delay(2000)
            clicked.value = false
            Data.buttonEnabled.value = true
            QuestionsData.buttonState.value = "none"
            QuestionsData.roundQuestion.value += 1
            if (QuestionsData.roundQuestion.value < QuestionsData.questionsToShow.size) {
                startTimer()
            }
        }
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
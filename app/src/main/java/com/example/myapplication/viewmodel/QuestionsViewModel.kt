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
    private var progressTimer: CountDownTimer? = null

    // si no los hago aquí los botones de las respuestas se cambian cada segundo
    fun startProgressTimer(){
        // resetear el valor de la barra de progreso
        Data.progressBar.value = 0
        // creo un temporizador(CountDownTimer) de cuenta progresiva (0-31(31 por que al empezar ya sube 1 segundo instantaneo))
        progressTimer = object : CountDownTimer(31000, 1000) {
            // cada segundo que pasa, el valor de la barra de progreso sube 1
            override fun onTick(millisUntilFinished: Long) {
                Data.progressBar.value++
            }
            // cuando el temporizador llega a 0, no hago nada, podria hacerlo
            override fun onFinish() {
            }
        }.start()
    }

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
                    RankingData.score.value -= 20
                    delay(2000)
                    QuestionsData.buttonState.value = "none"
                    QuestionsData.roundQuestion.value += 1
                    Data.buttonEnabled.value = true
                    if (QuestionsData.roundQuestion.value < QuestionsData.questionsToShow.size) {
                        startTimer()
                        startProgressTimer()
                    }
                }
            }
        }.start()
    }

    fun resetTimer() {
        timer?.cancel()
        progressTimer?.cancel()
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
            // añado los puntos en funcion del tiempo que queda
            RankingData.score.value += Data.counter.value
            QuestionsData.buttonState.value = "correct"
        }
        else {
            RankingData.score.value -= 10
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
                startProgressTimer()
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
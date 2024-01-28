package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.example.myapplication.room.RoomDatabase
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.view.Nav
import com.example.myapplication.viewmodel.QuestionsViewModel
import com.example.myapplication.viewmodel.RankingViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val database = Room.databaseBuilder(
                        this,
                        RoomDatabase::class.java,
                        "db_quizz" // nombre de la base de datos
                    ).build()
                    val questionsViewModel = QuestionsViewModel(database.QuestionsDao())
                    val rankingViewModel = RankingViewModel(database.RankingDao())
                    Nav(questionsViewModel, rankingViewModel)
                }
            }
        }
    }
}

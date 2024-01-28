package com.example.myapplication.view

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.viewmodel.QuestionsViewModel
import com.example.myapplication.viewmodel.RankingViewModel

@Composable
fun Nav(questionsViewModel: QuestionsViewModel, rankingViewModel: RankingViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "start"){
        composable("start"){
            StartScreen(navController)
        }
        composable("game"){
            GameScreen(navController, questionsViewModel, rankingViewModel)
        }
        composable("ranking"){
            RankingScreen(navController, rankingViewModel)
        }
        composable("addQuestions"){
            QuestionScreen(navController, questionsViewModel)
        }
    }
}
package com.example.myapplication.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.example.myapplication.models.RankingData
import com.example.myapplication.viewmodel.RankingViewModel

@Composable
fun RankingScreen(
    navController: NavController,
    rankingViewModel: RankingViewModel
) {
    LaunchedEffect(rankingViewModel){
        rankingViewModel.getRanking()
    }
    Column {
        if (RankingData.rankingList.isEmpty()) {
            Text(
                text = "No hay ranking"
            )
        }else{
            for (ranking in RankingData.rankingList) {
                Text(
                    text = ranking.user
                )
                Text(
                    text = ranking.score.toString()
                )
            }
        }
    }
}
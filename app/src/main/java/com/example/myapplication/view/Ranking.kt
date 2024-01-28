package com.example.myapplication.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.example.myapplication.models.RankingData
import com.example.myapplication.viewmodel.RankingViewModel

@Composable
fun RankingScreen(
    navController: NavController,
    rankingViewModel: RankingViewModel
) {
    val rankingList by remember{RankingData.rankingList}
    LaunchedEffect(rankingViewModel){
        rankingViewModel.getRanking()
    }
    Column {
        if (rankingList.isEmpty()) {
            Text(
                text = "No hay ranking"
            )
        }else{
            for (ranking in rankingList) {
                 Text(
                    text = ranking.user + ": " + ranking.score.toString()
                 )
            }
        }
    }
}
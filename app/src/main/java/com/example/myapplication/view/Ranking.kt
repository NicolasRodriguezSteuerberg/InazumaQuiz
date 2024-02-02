package com.example.myapplication.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.models.Ranking
import com.example.myapplication.models.RankingData
import com.example.myapplication.viewmodel.RankingViewModel

@Composable
fun RankingScreen(
    navController: NavController,
    rankingViewModel: RankingViewModel
) {
    // Get screen size to set the padding
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val padding = screenHeight * 0.1f

    LaunchedEffect(rankingViewModel){
        rankingViewModel.getRanking()
    }

    val rankingList by remember{RankingData.rankingList}

    Box(modifier = Modifier.fillMaxSize()){
        Box(modifier = Modifier
            .fillMaxHeight(0.1f)
            .fillMaxWidth()
            .align(Alignment.TopCenter)
        ){
            Text(
                text = "Ranking",
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight(0.75f)
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = padding.dp)
        ){
            ranking(
                modifier = Modifier.fillMaxSize(),
                rankingList
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight(0.25f)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ){
            Button(
                onClick = {
                    /*TODO*/
                },
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(
                    text = "Volver al inicio"
                )
            }
        }
    }
}

@Composable
fun ranking(modifier: Modifier, rankingList: List<Ranking>) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if (rankingList.isEmpty()) {
            Text(
                text = "No hay ranking"
            )
        }else{
            for ((position, ranking) in rankingList.withIndex()) {
                position(position, ranking)
            }
        }
    }
}

@Composable
fun position(position: Int, ranking: Ranking){
    Surface (
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .height(40.dp)
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth(1f)
                .background(Color(0xFF05A5B9)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Surface(
                modifier = Modifier
                    .padding(5.dp)
                    .size(30.dp),
                shape = CircleShape,
                color = when (position + 1) {
                    1 -> Color(0xFFFFBF00)
                    2 -> Color(0xFFE3E4E5)
                    3 -> Color(0xFFCD7F32)
                    else -> Color.White
                }
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = (position + 1).toString(), color = Color.Black
                    )
                }
            }
            Text(
                text = ranking.user
            )
            Text(
                modifier = Modifier.padding(5.dp),
                text = ranking.score.toString()
            )
        }
    }
}
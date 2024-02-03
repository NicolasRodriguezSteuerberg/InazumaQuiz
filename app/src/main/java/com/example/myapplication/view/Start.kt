package com.example.myapplication.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.models.Data
import com.example.myapplication.models.QuestionsData
import com.example.myapplication.models.RankingData


@Composable
fun StartScreen(
    navController: NavController
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val aspectratio = screenWidth/screenHeight

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.start),
            contentDescription = "Fondo de pantalla de inicio",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(aspectratio.times(2.5f))
                .graphicsLayer(
                    scaleX = LocalDensity.current.density,
                    scaleY = LocalDensity.current.density
                )
        )
        Start(Modifier.align(Alignment.BottomCenter),navController)
    }
}

/**
 * Screen of the start of the app
 * @param modifier Modifier
 * @param navController NavController
 */
@Composable
fun Start(modifier: Modifier, navController: NavController) {
    // Reset values to start a new game
    QuestionsData.roundQuestion.value = 0
    RankingData.score.value = 0
    // Initialize the questions
    Data.questionLoaded.value = false
    QuestionsData.questionsToShow = listOf()

    Column (modifier = modifier){
        StartButton(navController)
        Spacer(modifier = Modifier.padding(2.dp))
        RankingButton(navController)
        Spacer(modifier = Modifier.padding(2.dp))
        AddQuestions(navController)
        Spacer(modifier = Modifier.padding(12.dp))
    }
}

/**
 * Button to change to the add questions screen
 * @param navController NavController
 * @param modifier Modifier
 */
@Composable
fun AddQuestions(navController: NavController){
    Button(
        onClick = {
            navController.navigate("addQuestions")
        },
        shape = CutCornerShape(50),
        modifier = Modifier.fillMaxWidth(0.75f),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF8D8987),
            contentColor = Color(0xFFFFCB63)
        )
    ) {
        Text(
            text = "Add Questions",
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun RankingButton(
    navController: NavController
) {
    Button(
        onClick = {
            navController.navigate("ranking")
        },
        shape = CutCornerShape(50),
        modifier = Modifier
            .fillMaxWidth(0.75f),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF8D8987),
            contentColor = Color(0xFFFFCB63)
        )
    ) {
        Text(
            text = "Ranking",
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun StartButton(
    navController: NavController
) {
    Button(
        onClick = {
            navController.navigate("game")
        },
        shape = CutCornerShape(50),
        modifier = Modifier.fillMaxWidth(0.75f),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF8D8987),
            contentColor = Color(0xFFFFCB63)
        )
    ) {
        Text(
            text = "Start",
            fontWeight = FontWeight.Bold
        )
    }
}
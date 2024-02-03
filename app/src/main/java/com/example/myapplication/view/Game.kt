package com.example.myapplication.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.models.Data
import com.example.myapplication.models.Questions
import com.example.myapplication.models.QuestionsData
import com.example.myapplication.models.Ranking
import com.example.myapplication.models.RankingData
import com.example.myapplication.viewmodel.QuestionsViewModel
import com.example.myapplication.viewmodel.RankingViewModel

@Composable
fun GameScreen(
    navController: NavController,
    questionsViewModel: QuestionsViewModel,
    rankingViewModel: RankingViewModel
) {
    // Get screen size to set the aspect ratio of the image
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val aspectratio = screenWidth/screenHeight

    // Get questions from database
    if(!Data.questionLoaded.value){
        LaunchedEffect("getQuestions"){
            questionsViewModel.getQuestions()
        }
    }


    DisposableEffect(Unit) {
        onDispose {
            // Detener el temporizador cuando el composable se desvincula
            questionsViewModel.resetTimer()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Background(aspectratio)
        if (QuestionsData.questionsToShow.isNotEmpty() && QuestionsData.roundQuestion.value < QuestionsData.questionsToShow.size){
            Log.d("ELEMENTS", QuestionsData.questionsToShow.toString())
            Box(modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
            ){
                RoundScoreText(Modifier.align(Alignment.TopCenter))
                Counter(Modifier.align(Alignment.BottomCenter), questionsViewModel)
            }
            Box(modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
            ){
                QuestionText(
                    Modifier.align(Alignment.TopCenter),
                    QuestionsData.questionsToShow[QuestionsData.roundQuestion.value].question
                )
                Buttons(
                    Modifier.align(Alignment.BottomCenter),
                    questionsViewModel,
                    QuestionsData.questionsToShow[QuestionsData.roundQuestion.value]
                )
            }
        } else if (QuestionsData.roundQuestion.value >= 10){
            finishQuestions(rankingViewModel = rankingViewModel, navController = navController)
        }
    }
}

@Composable
fun Counter(modifier: Modifier, questionsViewModel: QuestionsViewModel) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${Data.counter.value}",
            fontWeight = FontWeight.Bold,
        )
    }
    LaunchedEffect("startTimer"){
        questionsViewModel.startTimer()
    }
}

@Composable
fun SaveButton(rankingViewModel: RankingViewModel, navController: NavController) {
    Button(onClick = {
        if (RankingData.nameTxt.value != "") {
            rankingViewModel.addRanking(
                Ranking(
                    user = RankingData.nameTxt.value,
                    score = RankingData.score.value
                )
            )
        }
        else {
            rankingViewModel.addRanking(
                Ranking(
                    user = "Anonimo",
                    score = RankingData.score.value
                )
            )
        }
        navController.navigate("start")
    }) {
        Text(
            text = "Save"
        )
    }
}

@Composable
fun NameScoreTxt() {
    TextField(
        value = RankingData.nameTxt.value,
        onValueChange = {
            RankingData.nameTxt.value = it
        }
    )
}

@Composable
fun RankingText() {
    Text(
        text = "Tu puntuacion ha sido de: ${RankingData.score.value}"
    )
}

@Composable
fun finishQuestions(rankingViewModel: RankingViewModel, navController: NavController) {
    Column {
        RankingText()
        Spacer(modifier = Modifier.padding(10.dp))
        NameScoreTxt()
        Spacer(modifier = Modifier.padding(10.dp))
        SaveButton(rankingViewModel, navController)
    }
}

@Composable
fun Background(aspectratio: Float) {
    Image(
        painter = painterResource(id = R.drawable.start2),
        contentDescription = "Inazuma",
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(aspectratio.times(2.8f))
            .graphicsLayer(
                scaleX = LocalDensity.current.density,
                scaleY = LocalDensity.current.density
            )
    )
}

@Composable
fun Buttons(
    modifier: Modifier,
    questionsViewModel: QuestionsViewModel,
    question: Questions
) {
        Column (modifier = modifier){
            QuestionsData.answers = listOf(question.correct, question.wrong1, question.wrong2).shuffled()
            QuestionsData.correctAnswer = question.correct
            QuestionsData.answers.forEach{ respuesta ->
                AnswerButton(respuesta, questionsViewModel)
                Spacer(modifier = Modifier.padding(10.dp))
            }
            Spacer(modifier = Modifier.padding(10.dp))
        }
}

@Composable
fun AnswerButton(it: String, questionsViewModel: QuestionsViewModel) {
    var clicked = remember { mutableStateOf(false) }
    Button(
        onClick = {
            Data.buttonEnabled.value = false
            clicked.value = true
            questionsViewModel.isCorrectAnswer(it, clicked)
        },
        shape = CutCornerShape(50),
        enabled = Data.buttonEnabled.value,
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .background(Color(0xFF0069A2),
        shape = CutCornerShape(50))
            .border(
                width = 1.5.dp,
                color = when {
                    clicked.value && QuestionsData.buttonState.value == "correct" -> Color(0xFF00FF00)
                    clicked.value && QuestionsData.buttonState.value == "wrong" -> Color(0xFFFF0000)
                    QuestionsData.buttonState.value == "timeOut" -> Color(0xFFFF8C00)
                    else -> Color.Transparent
                },
                shape = CutCornerShape(50)
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF0069A2),
            contentColor = Color(0xFFFFCB63),
            disabledContainerColor = Color(0xFF0069A2),
            disabledContentColor = Color(0xFFFFCB63)
        )
    ){
        Text(
            text = it
        )
    }
}

@Composable
fun QuestionText(modifier: Modifier,question: String) {
    Column(modifier = modifier) {
        Text(
            text = question,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .background(Color(0xA960EAFC))
                .border(
                    width = 5.dp,
                    color = Color(0xFF0069A2)
                )
                .padding(10.dp),
            fontWeight = FontWeight.Bold,
            color = Color(0xFF000000),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun RoundScoreText(modifier: Modifier) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ){
        Text(
            text = "Round: ${QuestionsData.roundQuestion.value + 1}",
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFF4900)
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Text(
            text = "Score: ${RankingData.score.value}",
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFF4900)
        )
    }
}



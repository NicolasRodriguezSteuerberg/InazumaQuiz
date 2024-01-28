package com.example.myapplication.view

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
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
    LaunchedEffect(questionsViewModel){
        questionsViewModel.getQuestions()
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
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
        if (QuestionsData.questionsToShow.isNotEmpty() && QuestionsData.roundQuestion.value < QuestionsData.questionsToShow.size){
            Box(modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
            ){
                RoundScoreText(Modifier.align(Alignment.TopCenter))
               /*
                CircularProgress(Modifier.align(Alignment.BottomCenter))
                Counter(Modifier.align(Alignment.BottomCenter))
                */
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
        }
        else if (QuestionsData.roundQuestion.value >= 10){
            Column {
                RankingText()
                Spacer(modifier = Modifier.padding(10.dp))
                NameScoreTxt()
                Spacer(modifier = Modifier.padding(10.dp))
                SaveButton(rankingViewModel, navController)
            }
        }
    }
}

@Composable
fun CircularProgress(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp),
    ) {
        Canvas(
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.CenterHorizontally),
        ){
            val radius = minOf( size.width, size.height) / 2f
            val strokeWidth = 4.dp.toPx()
            val sweepAngle = (360 * QuestionsData.counterTime.value).toFloat()
            drawArc(
                color = Color(0xFFFF5722),
                startAngle = 270f,
                sweepAngle = sweepAngle,
                useCenter = true,
                size = Size(radius * 2, radius * 2),
                style = Stroke(strokeWidth)
            )
        }
    }
}

@Composable
fun Counter(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${QuestionsData.counterTime.value}",
            fontWeight = FontWeight.Bold,
        )
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
    Button(
        onClick = {
            questionsViewModel.isCorrectAnswer(it)
        },
        shape = CutCornerShape(50),
        modifier = Modifier.fillMaxWidth(0.75f),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF0069A2),
            contentColor = Color(0xFFFFCB63)
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
                .fillMaxWidth(0.9f)
                .background(Color(0xA960EAFC)),
            fontWeight = FontWeight.Bold,
            color = Color(0xFF000000)
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



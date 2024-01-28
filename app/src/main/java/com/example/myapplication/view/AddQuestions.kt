package com.example.myapplication.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.models.Questions
import com.example.myapplication.models.QuestionsData
import com.example.myapplication.models.RankingData
import com.example.myapplication.viewmodel.QuestionsViewModel

@Composable
fun QuestionScreen(
    navController: NavHostController,
    questionsViewModel: QuestionsViewModel
){
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Question(
            modifier = Modifier.align(Alignment.TopCenter),
            navController = navController,
            questionsViewModel = questionsViewModel
        )
    }
}

@Composable
fun Question(modifier: Modifier, navController: NavHostController, questionsViewModel: QuestionsViewModel) {
    Column(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.padding(20.dp))
        QuestionTxt()
        Spacer(modifier = Modifier.padding(8.dp))
        CorrectAnswerTxt()
        Spacer(modifier = Modifier.padding(8.dp))
        WrongAnswer1Txt()
        Spacer(modifier = Modifier.padding(8.dp))
        WrongAnswer2Txt()
        Spacer(modifier = Modifier.padding(8.dp))
        AddQuestionButton(questionsViewModel)
        Spacer(modifier = Modifier.padding(8.dp))
        BackButton(navController)
    }
}

@Composable
fun BackButton(navController: NavHostController) {
    Button(
        onClick = {
            navController.navigate("start")
        },
    ) {
        Text(
            text = "Back"
        )
    }
}

@Composable
fun AddQuestionButton(questionsViewModel: QuestionsViewModel) {
    Button(
        onClick = {
            if (
                QuestionsData.questionTxt.value != "" &&
                QuestionsData.correctAnswerTxt.value != "" &&
                QuestionsData.wrongAnswer1Txt.value != "" &&
                QuestionsData.wrongAnswer2Txt.value != ""
            ) {
                questionsViewModel.addQuestion(
                    Questions(
                        question = QuestionsData.questionTxt.value,
                        correct = QuestionsData.correctAnswerTxt.value,
                        wrong1 = QuestionsData.wrongAnswer1Txt.value,
                        wrong2 = QuestionsData.wrongAnswer2Txt.value
                    )
                )
            }
        },
    ) {
        Text(
            text = "Add Question"
        )
    }
}

@Composable
fun WrongAnswer1Txt() {
    TextField(
        value = QuestionsData.wrongAnswer1Txt.value,
        onValueChange = {
            QuestionsData.wrongAnswer1Txt.value = it
        },
        modifier = Modifier
            .fillMaxWidth(0.75f)
        ,
        textStyle = LocalTextStyle.current.copy(color = Color(0xFFF18D65)),
        placeholder = {
            Text(
                text = "Wrong Answer 1",
                color = Color(0xFFF18D65)
            )
        }
    )
}

@Composable
fun WrongAnswer2Txt() {
    TextField(
        value = QuestionsData.wrongAnswer2Txt.value,
        onValueChange = {
            QuestionsData.wrongAnswer2Txt.value = it
        },
        modifier = Modifier
            .fillMaxWidth(0.75f)
        ,
        textStyle = LocalTextStyle.current.copy(color = Color(0xFFF18D65)),
        placeholder = {
            Text(
                text = "Wrong Answer 2",
                color = Color(0xFFF18D65)
            )
        }
    )
}

@Composable
fun CorrectAnswerTxt() {
    TextField(
        value = QuestionsData.correctAnswerTxt.value,
        onValueChange = {
            QuestionsData.correctAnswerTxt.value = it
        },
        modifier = Modifier
            .fillMaxWidth(0.75f)
        ,
        textStyle = LocalTextStyle.current.copy(color = Color(0xFFF18D65)),
        placeholder = {
            Text(
                text = "Correct Answer",
                color = Color(0xFFF18D65)
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionTxt(){
    TextField(
        value = QuestionsData.questionTxt.value,
        onValueChange = {
            QuestionsData.questionTxt.value = it
        },
        modifier = Modifier
            .fillMaxWidth(0.75f)
        ,
        textStyle = LocalTextStyle.current.copy(color = Color(0xFFF18D65)),
        placeholder = {
            Text(
                text = "Question",
                color = Color(0xFFF18D65)
            )
        }
    )
}
package com.example.myapplication.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.models.Questions
import com.example.myapplication.models.QuestionsData
import com.example.myapplication.viewmodel.QuestionsViewModel

@Composable
fun QuestionScreen(
    navController: NavHostController,
    questionsViewModel: QuestionsViewModel
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF393939))
    ){
        Box(
            modifier = Modifier
                .fillMaxHeight(0.2f)
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ){
            Logo(modifier = Modifier.align(Alignment.Center))
        }
        Box(
            modifier = Modifier
                .fillMaxHeight(0.6f)
                .fillMaxWidth()
                .align(Alignment.Center)
        ){
            Question(modifier = Modifier.align(Alignment.TopCenter))
        }
        // caja para los botones
        Box(
            modifier = Modifier
                .fillMaxHeight(0.2f)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ){
            ButtonsAddBack(
                modifier = Modifier.align(Alignment.TopCenter),
                navController = navController,
                questionsViewModel = questionsViewModel
            )
        }
    }
}
@Composable
fun ButtonsAddBack(
    questionsViewModel: QuestionsViewModel,
    navController: NavController,
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ){
        AddQuestionButton(questionsViewModel = questionsViewModel)
        Spacer(modifier = Modifier.padding(8.dp))
        BackButton(navController = navController)
    }
}

@Composable
fun Logo(modifier: Modifier){
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "Logo de Inazuma Eleven",
        modifier = modifier
    )
}

@Composable
fun Question(modifier: Modifier) {
    Column(
        modifier = modifier
    ) {
        QuestionTxt()
        Spacer(modifier = Modifier.padding(8.dp))
        CorrectAnswerTxt()
        Spacer(modifier = Modifier.padding(8.dp))
        WrongAnswer1Txt()
        Spacer(modifier = Modifier.padding(8.dp))
        WrongAnswer2Txt()
        Spacer(modifier = Modifier.padding(8.dp))
    }
}

@Composable
fun BackButton(navController: NavController) {
    Button(
        onClick = {
            navController.navigate("start")
        },
        modifier = Modifier
            .fillMaxWidth(0.75f),
        shape = CutCornerShape(0),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF8D8987),
            contentColor = Color(0xFFFFCB63)
        )
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
        modifier = Modifier
            .fillMaxWidth(0.75f),
        shape = CutCornerShape(0),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF8D8987),
            contentColor = Color(0xFFFFCB63)
        )
    ) {
        Text(
            text = "Add Question"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WrongAnswer1Txt() {
    TextField(
        value = QuestionsData.wrongAnswer1Txt.value,
        onValueChange = {
            QuestionsData.wrongAnswer1Txt.value = it
        },
        modifier = Modifier.fillMaxWidth(0.75f),
        textStyle = LocalTextStyle.current.copy(color = Color(0xFFFFCB63)),
        placeholder = {
            Text(
                text = "Wrong Answer 1",
                color = Color(0xA6FFCB63)
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(0xFF8D8987)
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
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
        textStyle = LocalTextStyle.current.copy(color = Color(0xFFFFCB63)),
        placeholder = {
            Text(
                text = "Wrong Answer 2",
                color = Color(0xA6FFCB63)
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(0xFF8D8987)
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
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
        textStyle = LocalTextStyle.current.copy(color = Color(0xFFFFCB63)),
        placeholder = {
            Text(
                text = "Correct Answer",
                color = Color(0xA6FFCB63)
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(0xFF8D8987)
        )

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
        textStyle = LocalTextStyle.current.copy(color = Color(0xFFFFCB63)),
        placeholder = {
            Text(
                text = "Question",
                color = Color(0xA6FFCB63)
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(0xFF8D8987)
        )
    )
}
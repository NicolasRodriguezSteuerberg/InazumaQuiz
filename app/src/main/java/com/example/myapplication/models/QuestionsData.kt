package com.example.myapplication.models

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

object QuestionsData {
    var questionsToShow = listOf<Questions>()
    var roundQuestion = mutableStateOf(0)
    var answers: List<String> = emptyList()
    var correctAnswer = ""
    var questionTxt = mutableStateOf("")
    var correctAnswerTxt = mutableStateOf("")
    var wrongAnswer1Txt = mutableStateOf("")
    var wrongAnswer2Txt = mutableStateOf("")
    var buttonState = mutableStateOf("ninguno")
    val addQuestions = listOf(
        Questions(
            question = "¿Cuál fue la primera supertecnica que se vio?",
            correct = "Triangulo Letal",
            wrong1 = "Mano Celestial",
            wrong2 = "Tornado de Fuego"
        ),
        Questions(
            question = "¿Quien fue el primer jugador en unirse en el reclutamiento antes del partido de la royal (Cuando eran 7 jugadores)?",
            correct = "Nathan",
            wrong1 = "Max",
            wrong2 = "Jim"
        ),
        Questions(
            question = "¿Quien fue el primero en unirse a la caravan inazuma?",
            correct = "Tory",
            wrong1 = "Shawn",
            wrong2 = "Darren"
        ),
        Questions(
            question = "¿Contra que equipo no jugó nunca el raimon?",
            correct = "Prominence",
            wrong1 = "Polvo de diamantes",
            wrong2 = "Caos"
        ),
        Questions(
            question = "¿Cómo marcó Asuto Inamori un gol a España?",
            correct = "Esquivando al portero y tirándose dentro de la portería",
            wrong1 = "Desde el medio campo con Shining Bird",
            wrong2 = "Regateando a todo el equipo y con su Shining Bird"
        ),
        Questions(
            question = "¿En IE orion quien resultó ser el segundo lobo de Inazuma Japón que filtraba información sobre la selección a Orión?",
            correct = "Shiro Fubuki",
            wrong1 = "Atsuya Fubuki",
            wrong2 = "Hiura Kirina"
        ),
        Questions(
            question = "¿En IE orion que equipo jugó en lugar de la selección de Estados Unidos?",
            correct = "Navy Invaders",
            wrong1 = "Unicorn",
            wrong2 = "Perfect Spark"
        ),
        Questions(
            question = "¿En IE orion quién es el segundo capitán de la selección?",
            correct = "Nosaka Yuuma",
            wrong1 = "Inamori Asuto",
            wrong2 = "Haizaki Ryouhei"
        ),
        Questions(
            question = "¿Que supertecnica usa el androide de Ray Dark en el partido contra Earth Eleven?",
            correct = "Tornado de Fuego",
            wrong1 = "Pingüino emperador X",
            wrong2 = "Coz"
        ),

        Questions(
            question = "¿Cual de estas es una supertecnica combinada de shun y hugh?",
            correct = "Cruce Explosivo",
            wrong1 = "Remate Doble",
            wrong2 = "Grito del Edén"
        ),
        Questions(
            question = "¿Que supertecnica NO es un regate?",
            correct = "Ataque afilado",
            wrong1 = "Coz 2",
            wrong2 = "Defensa Holográfica"
        ),
        Questions(
            question = "¿Cual es el espiritu guerrero de Golddie Lemón?",
            correct = "Guerrera Del Amanecer, Amateratsu",
            wrong1 = "Valquiria Abanderada, Brunilda",
            wrong2 = "Colablanca Explosiva, Amorosa"
        ),
    )
}
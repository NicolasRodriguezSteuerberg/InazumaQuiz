package com.example.myapplication.models

import androidx.compose.runtime.mutableStateOf

object Data {
    var counter = mutableStateOf(31)
    var buttonEnabled = mutableStateOf(true)
    var questionLoaded = mutableStateOf(false)
    var progressBar = mutableStateOf(0)
    var maxProgressBar = 31
}
package com.example.ui.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.ui.graphics.Color


fun getBackgroundColor(genderType: String?) = when (genderType) {
    "Male" -> Color(0xFF82BDE9)
    "Female" -> Color(0xFFE982D2)
    else -> Color.Gray
}

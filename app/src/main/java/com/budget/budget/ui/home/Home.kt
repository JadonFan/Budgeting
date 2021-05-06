package com.budget.budget.ui.home
//
//import android.view.Gravity
//import androidx.compose.foundation.Text
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.CircularProgressIndicator
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.budgetapp.components.CircularProgressBar
//
//@Composable
//fun HomeScreen(weekText: String, currWeekText: String, currTargetText: String, progress: Float) {
//    Stack(modifier = Modifier.fillMaxSize()) {
//        Column(modifier = Modifier.fillMaxWidth()) {
//            Text(
//                text = weekText,
//                modifier = Modifier.gravity(Alignment.CenterHorizontally),
//                fontSize = 20.sp
//            )
//            Text(
//                text = currWeekText,
//                modifier = Modifier.gravity(Alignment.CenterHorizontally),
//                fontSize = 24.sp
//            )
//            Text(
//                text = currTargetText,
//                modifier = Modifier.gravity(Alignment.CenterHorizontally),
//                fontSize = 24.sp
//            )
//        }
//        CircularProgressIndicator(progress = progress, modifier = Modifier.width(200.dp).height(200.dp))
//    }
//}
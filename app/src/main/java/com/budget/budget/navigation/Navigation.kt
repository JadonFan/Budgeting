package com.budget.budget.navigation
//
//import androidx.compose.foundation.Icon
//import androidx.compose.foundation.Text
//import androidx.compose.material.BottomNavigation
//import androidx.compose.material.BottomNavigationItem
//import androidx.compose.material.TopAppBar
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Analytics
//import androidx.compose.material.icons.filled.Explore
//import androidx.compose.material.icons.filled.Timeline
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.vectorResource
//import com.example.budgetapp.R
//
//val bottomNavigationItems = hashMapOf(
//        "Report" to Icons.Filled.Analytics,
//        "Tracker" to Icons.Filled.Timeline,
//        "News & Tips" to Icons.Filled.Explore
//    )
//
//@Composable
//fun TopView() {
//    TopAppBar(title = { Text("Budget Assistance") })
//}
//
//@Composable
//fun BottomView() {
//    val selectedItem = remember { mutableStateOf(0) }
//    BottomNavigation {
//        bottomNavigationItems.entries.forEachIndexed { index, item ->
//            BottomNavigationItem(
//                icon = { Icon(item.value) },
//                label = { Text(item.key) },
//                selected = selectedItem.value == index,
//                onSelect = { selectedItem.value = index }
//            )
//        }
//    }
//}
//
//@Composable
//fun Scaffold() {
//}
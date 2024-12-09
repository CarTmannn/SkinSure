package com.example.skinsure.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.skinsure.viewModel.IngredientsDetailViewModel

@Composable
fun HistoryDetail(ingredientsDetailViewModel: IngredientsDetailViewModel = viewModel()) {
    val data by ingredientsDetailViewModel.IngredientsDetail.collectAsState()

    LaunchedEffect(Unit){
        ingredientsDetailViewModel.getIngredientDetail(ingredientsDetailViewModel.tempName.value)
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(color = Color(0XFFF5EFEF))
    ) {
        if (data != null) {
            Column {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .heightIn(140.dp)
                        .shadow(elevation = 5.dp)
                        .background(color = Color(0XFFf5efef)),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, bottom = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowLeft,
                            contentDescription = "",
                            tint = Color.Black,
                            modifier = Modifier.size(50.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Face Wash",
                            color = Color.Black,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier = Modifier
                        .size(150.dp)
                        .background(color = Color.Gray)){

                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(color = Color.Yellow)){
                        IngredientListBox(name = "Propanediol", risk = "Moderate", onBoxClicked = {})
                    }
                }
            }
        } else {
            Box(Modifier.fillMaxSize()) {
                /*CircularProgressIndicator(
                    color = Color.Gray,
                    modifier = Modifier.size(30.dp),
                    strokeWidth = 5.dp
                )*/
            }
        }
    }
}
package com.example.skinsure.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skinsure.R

@Composable
fun Intro(){
    Box(
        Modifier
            .fillMaxSize()
            .background(color = Color.White)) {
        Column(Modifier.align(Alignment.BottomCenter)) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(475.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                    )
                    .background(
                        color = Color(0XFFf5efef),
                        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                    ), contentAlignment = Alignment.TopCenter) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(top = 60.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Welcome to SkinSure", color = Color.Black, fontWeight = FontWeight.W900, fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(100.dp))
                    Text(text = "Scan and ensure your beauty products are safe", textAlign = TextAlign.Center, fontSize = 18.sp, modifier = Modifier.padding(horizontal = 100.dp))
                    Spacer(modifier = Modifier.height(30.dp))

                }
            }
        }
        Column(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 30.dp)) {
            Box(
                Modifier
                    .width(300.dp)
                    .height(65.dp)
                    .background(
                        color = Color(0XFFC79898),
                        shape = RoundedCornerShape(50.dp)
                    ), contentAlignment = Alignment.Center) {
                Text(text = "Get Started", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
        Column(
            Modifier
                .align(Alignment.TopCenter)
                .padding(bottom = 30.dp)
                .offset(y = 110.dp)) {
            Box(
                Modifier
                    .width(300.dp)
                    .height(150.dp), contentAlignment = Alignment.Center) {
                Image(painter = painterResource(id = R.drawable.logo), contentDescription = "", contentScale = ContentScale.Fit, modifier = Modifier.fillMaxSize())
            }
        }
    }
}
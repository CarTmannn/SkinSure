package com.example.skinsure.navigations

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.skinsure.screens.DetailIngredients
import com.example.skinsure.screens.IngredientList
import com.example.skinsure.screens.IngredientScanScreen
import com.example.skinsure.screens.Scan
import com.example.skinsure.viewModel.IngredientViewModel
import com.example.skinsure.viewModel.IngredientsDetailViewModel

@Composable
fun navigation(){
    val navController = rememberNavController()
    val IngredientViewModel : IngredientViewModel = viewModel()
    val IngredientsDetailViewModel : IngredientsDetailViewModel = viewModel()
    
    NavHost(navController = navController, startDestination = "scan"){
        composable("home") { IngredientScanScreen(viewModel = IngredientViewModel, ingredientsDetailViewModel = IngredientsDetailViewModel, navController = navController) }
        composable("detailIngredient") { DetailIngredients(ingredientsDetailViewModel = IngredientsDetailViewModel) }
        composable("scan") { Scan(navController = navController, ingredientViewModel = IngredientViewModel) }
        composable("list") { IngredientList(navController = navController, ingredientViewModel = IngredientViewModel, ingredientsDetailViewModel = IngredientsDetailViewModel) }
    }
}
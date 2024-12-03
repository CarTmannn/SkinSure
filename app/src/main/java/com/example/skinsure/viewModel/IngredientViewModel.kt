package com.example.skinsure.viewModel

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skinsure.model.IngredientsModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class IngredientViewModel : ViewModel() {

    var _extractedText = mutableStateOf<List<String>>(emptyList())
    val extractedText get() = _extractedText

    var _extractionStatus = mutableStateOf("Belum ada ekstraksi") // Status ekstraksi
    val extractionStatus get() = _extractionStatus

    var _ingredientDetails = MutableStateFlow<List<IngredientsModel>>(emptyList())
    val ingredientDetails: StateFlow<List<IngredientsModel>> get() = _ingredientDetails

    private var _searchResults = MutableStateFlow<List<IngredientsModel>>(emptyList())
    val searchResults: StateFlow<List<IngredientsModel>> = _searchResults

    private var _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _imageBitmap = MutableStateFlow<Bitmap?>(null)
    val imageBitmap: StateFlow<Bitmap?> get() = _imageBitmap

    private val db = FirebaseFirestore.getInstance()

    fun setImage(image: Bitmap){
        _imageBitmap.value = image
    }

    fun setExtractionStatus(status: String) {
        _extractionStatus.value = status
    }

    fun setIngredientDetailsBackToEmpty(){
        _ingredientDetails.value = emptyList()
    }

     fun extractTextFromImage(bitmap: Bitmap) {
        setExtractionStatus("Proses Ekstraksi...")
        _isLoading.value = true
        val textRecognizer = com.google.mlkit.vision.text.TextRecognition.getClient(
            TextRecognizerOptions.DEFAULT_OPTIONS)
        val inputImage = com.google.mlkit.vision.common.InputImage.fromBitmap(bitmap, 0)

        textRecognizer.process(inputImage)
            .addOnSuccessListener { visionText ->
                val ingredientsText = StringBuilder()
                for (block in visionText.textBlocks) {
                    for (line in block.lines) {
                        ingredientsText.append(line.text).append(" ")
                    }
                }

                val cleanedIngredients = cleanAndFormatIngredients(ingredientsText.toString())

                _extractedText.value = cleanedIngredients
                Log.d("ExtractedText", cleanedIngredients.joinToString(", ")) // Log hasil ekstraksi

                setExtractionStatus("Ekstraksi Berhasil")

                viewModelScope.launch {
                    searchIngredientsInFirebase(cleanedIngredients)
                }
            }
            .addOnFailureListener { e ->
                setExtractionStatus("Ekstraksi Gagal: ${e.message}")
                _isLoading.value = false
            }
    }

    private fun cleanAndFormatIngredients(ingredientsText: String): List<String> {
        // Replace newline characters with spaces and normalize spaces
        val formattedText = ingredientsText.replace("\n", " ").trim()

        // Improved regex to capture both uppercase and lowercase words, and ingredients with slashes
        val regex = Regex("""([A-Za-z0-9/-]+(?:\s[A-Za-z0-9/-]+)*(?:\s\([\w\s]+\))?(?:\s[A-Za-z0-9/-]+)*)""")


        Log.d("RawTextBeforeCleaning", "Raw Text: ${ingredientsText}")
        val ingredients = regex.findAll(formattedText)
            .map { it.value.trim() } // Remove leading and trailing spaces
            .filter { it.isNotEmpty() } // Filter out empty strings
            .filterNot { it.contains("Ingredients:", ignoreCase = true) } // Remove the "Ingredients:" label
            .distinct() // Remove duplicates
            .toList()

        Log.d("CleanedIngredients", "Cleaned Ingredients: ${ingredients.joinToString(", ")}")

        return ingredients
    }

    suspend fun searchIngredientsInFirebase(ingredientsList: List<String>) {
        viewModelScope.launch {
            _isLoading.value = true // Tetap set loading ke true di awal
            val results = mutableListOf<IngredientsModel>() // Menampung semua hasil pencarian
            val normalizedIngredientsList = ingredientsList.map { it.trim().lowercase() }

            Log.d("NormalizedIngredients", normalizedIngredientsList.joinToString(", "))

            for (ingredient in normalizedIngredientsList) { // Menggunakan for loop
                try {
                    val snapshot = db.collection("ingredients")
                        .whereEqualTo("IngredientName", ingredient)
                        .get()
                        .await()

                    if (snapshot.isEmpty) {
                        Log.d("FirebaseQueryResult", "No ingredients found for: $ingredient")
                    } else {
                        snapshot.documents.mapNotNull { document ->
                            val data = document.toObject(IngredientsModel::class.java)
                            if (data != null) {
                                results.add(data)
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.e("FirebaseError", "Error fetching ingredient: $ingredient", e)
                }
            }

            _ingredientDetails.value = results
            Log.d("IngredientDetails", results.toString())
            _isLoading.value = false
        }
    }


    fun search(query: String) {
        viewModelScope.launch {
            if (query.isNotBlank()) {
                val results = try {
                    db.collection("ingredients")
                        .whereGreaterThanOrEqualTo("IngredientName", query)
                        .whereLessThanOrEqualTo("IngredientName", query + "\uf8ff")
                        .get()
                        .await()
                        .documents.mapNotNull {
                                documents -> documents.toObject(IngredientsModel::class.java)
                        }
                } catch (e: Exception) {
                    emptyList()
                }
                _searchResults.value = results
            } else {
                _searchResults.value = emptyList()
            }
        }
    }
}

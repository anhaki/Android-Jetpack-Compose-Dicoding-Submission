package com.haki.hqrecipe.screen.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haki.hqrecipe.data.Repository
import com.haki.hqrecipe.data.ResultState
import com.haki.hqrecipe.model.RecipeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class SavedViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _resultState: MutableStateFlow<ResultState<List<RecipeModel>>> = MutableStateFlow(
        ResultState.Loading)

    val resultState: StateFlow<ResultState<List<RecipeModel>>>
        get() = _resultState

    fun getSavedRecipe() {
        viewModelScope.launch {
            repository.getSavedRecipe()
                .catch {
                    _resultState.value = ResultState.Error(it.message.toString())
                }
                .collect { recipes ->
                    _resultState.value = ResultState.Success(recipes)

                }
        }
    }

    fun saveRecipe(recipeId: Long) {
        viewModelScope.launch {
            repository.updateSavedRecipe(recipeId)
        }
    }
}
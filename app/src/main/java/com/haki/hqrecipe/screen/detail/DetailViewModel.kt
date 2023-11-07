package com.haki.hqrecipe.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haki.hqrecipe.data.Repository
import com.haki.hqrecipe.data.ResultState
import com.haki.hqrecipe.model.RecipeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: Repository
): ViewModel() {
    private val _resultState: MutableStateFlow<ResultState<RecipeModel>> =
        MutableStateFlow(ResultState.Loading)
    val resultState: StateFlow<ResultState<RecipeModel>>
        get() = _resultState

    fun getRecipeById(recipeId: Long) {
        viewModelScope.launch {
            _resultState.value = ResultState.Loading
            _resultState.value = ResultState.Success(repository.getRecipeById(recipeId))
        }
    }

}
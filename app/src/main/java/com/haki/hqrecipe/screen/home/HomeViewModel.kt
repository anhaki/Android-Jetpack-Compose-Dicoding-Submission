package com.haki.hqrecipe.screen.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haki.hqrecipe.data.Repository
import com.haki.hqrecipe.data.ResultState
import com.haki.hqrecipe.model.RecipeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _resultState: MutableStateFlow<ResultState<List<RecipeModel>>> = MutableStateFlow(ResultState.Loading)

    val resultState: StateFlow<ResultState<List<RecipeModel>>>
        get() = _resultState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun getAllRecipe() {
        viewModelScope.launch {
            repository.getRecipes()
                .catch {
                    _resultState.value = ResultState.Error(it.message.toString())
                }
                .collect { recipes ->
                    _resultState.value = ResultState.Success(recipes)

                }
        }
    }

    fun getSearchRecipe(newQuery: String) {
        viewModelScope.launch {
            _query.value = newQuery
            repository.searchRecipes(_query.value)
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
package com.haki.hqrecipe.data

import com.haki.hqrecipe.model.RecipeModel
import com.haki.hqrecipe.model.RecipesData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class Repository {
    private val recipes = mutableListOf<RecipeModel>()

    init {
        if (recipes.isEmpty()) {
            RecipesData.recipes.forEach {
                recipes.add(it)
            }
        }
    }

    fun getRecipes(): Flow<List<RecipeModel>> {
        return flowOf(recipes)
    }

    fun searchRecipes(query: String): Flow<List<RecipeModel>> {
        return flowOf(
            recipes.filter {
                it.name.contains(query, ignoreCase = true)
            }
        )
    }

    fun getRecipeById(recipeId: Long): RecipeModel {
        return recipes.first {
            it.id == recipeId
        }
    }

    fun updateSavedRecipe(recipeId: Long): Flow<Boolean> {
        val index = recipes.indexOfFirst { it.id == recipeId }
        val result = if (index >= 0) {
            val recipe = recipes[index]
            recipes[index] =
                recipe.copy(isSaved = !recipe.isSaved)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getSavedRecipe(): Flow<List<RecipeModel>> {
        return getRecipes()
            .map { recipes ->
                recipes.filter { theRecipe ->
                    theRecipe.isSaved
                }
            }
    }

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(): Repository =
            instance ?: synchronized(this) {
                Repository().apply {
                    instance = this
                }
            }
    }
}
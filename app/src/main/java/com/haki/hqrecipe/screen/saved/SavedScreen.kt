package com.haki.hqrecipe.screen.saved

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.haki.hqrecipe.ViewModelFactory
import com.haki.hqrecipe.components.MySearchBar
import com.haki.hqrecipe.components.RecipeItem
import com.haki.hqrecipe.data.ResultState
import com.haki.hqrecipe.di.Injection
import com.haki.hqrecipe.model.RecipeModel
import com.haki.hqrecipe.screen.home.HomeViewModel
import com.haki.hqrecipe.ui.theme.genBg
import com.haki.hqrecipe.util.Urbanist

@Composable
fun SavedScreen(
    modifier: Modifier = Modifier,
    viewModel: SavedViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    Box(modifier = modifier
        .fillMaxSize()
        .background(genBg)){
        viewModel.resultState.collectAsState(initial = ResultState.Loading).value.let { result ->
            when (result) {
                is ResultState.Loading -> {
                    viewModel.getSavedRecipe()
                }
                is ResultState.Success -> {
                    if(result.data.isEmpty()){
                        Text(
                            modifier = modifier.align(Alignment.Center),
                            text = "There's no saved recipe",
                            fontFamily = Urbanist
                        )
                    }
                    else{
                        SavedContent(
                            recipes = result.data,
                            modifier = modifier,
                            navigateToDetail = navigateToDetail,
                            onSave = {id ->
                                viewModel.saveRecipe(recipeId = id)
                                viewModel.getSavedRecipe()

                            }
                        )
                    }
                }
                is ResultState.Error -> {}
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SavedContent(
    recipes: List<RecipeModel>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
    onSave: (id: Long) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(recipes, key = { it.id + 1 }) { data ->
            RecipeItem(
                photoUrl = data.photo,
                id = data.id,
                name = data.name,
                isSaved = data.isSaved,
                modifier = Modifier
                    .clickable {
                    navigateToDetail(data.id) }
                    .animateItemPlacement(tween(durationMillis = 250)),
                onSave = onSave
            )
        }
    }

}
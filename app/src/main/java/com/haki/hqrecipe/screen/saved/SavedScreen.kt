package com.haki.hqrecipe.screen.saved

import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.haki.hqrecipe.R
import com.haki.hqrecipe.ViewModelFactory
import com.haki.hqrecipe.components.RecipeItem
import com.haki.hqrecipe.data.ResultState
import com.haki.hqrecipe.di.Injection
import com.haki.hqrecipe.model.RecipeModel
import com.haki.hqrecipe.ui.theme.botBg
import com.haki.hqrecipe.ui.theme.genBg
import com.haki.hqrecipe.ui.theme.selectedItem
import com.haki.hqrecipe.util.Urbanist

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedScreen(
    modifier: Modifier = Modifier,
    viewModel: SavedViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    val context = LocalContext.current

    Column(modifier = modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.saved_recipe),
                    color = selectedItem,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = Urbanist
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(botBg),
            modifier = Modifier
        )

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(genBg)
        ) {
            viewModel.resultState.collectAsState(initial = ResultState.Loading).value.let { result ->
                when (result) {
                    is ResultState.Loading -> {
                        viewModel.getSavedRecipe()
                    }

                    is ResultState.Success -> {
                        if (result.data.isEmpty()) {
                            Text(
                                modifier = modifier
                                    .align(Alignment.Center)
                                    .testTag("saved_error"),
                                text = stringResource(id = R.string.error_saved),
                                fontFamily = Urbanist,
                                color = selectedItem
                            )
                        } else {
                            SavedContent(
                                recipes = result.data,
                                modifier = modifier,
                                navigateToDetail = navigateToDetail,
                                onSave = { id ->
                                    viewModel.saveRecipe(recipeId = id)
                                    viewModel.getSavedRecipe()

                                }
                            )
                        }
                    }

                    is ResultState.Error -> {
                        Toast.makeText(
                            context,
                            stringResource(id = R.string.error),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
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
        modifier = modifier.testTag("SavedList")
    ) {
        items(recipes, key = { it.id }) { data ->
            RecipeItem(
                photoUrl = data.photo,
                id = data.id,
                name = data.name,
                isSaved = data.isSaved,
                modifier = Modifier
                    .clickable {
                        navigateToDetail(data.id)
                    }
                    .animateItemPlacement(tween(durationMillis = 250)),
                onSave = onSave
            )
        }
    }

}
package com.haki.hqrecipe.screen.home

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.haki.hqrecipe.R
import com.haki.hqrecipe.ViewModelFactory
import com.haki.hqrecipe.components.MySearchBar
import com.haki.hqrecipe.components.RecipeItem
import com.haki.hqrecipe.data.ResultState
import com.haki.hqrecipe.di.Injection
import com.haki.hqrecipe.model.RecipeModel
import com.haki.hqrecipe.ui.theme.genBg
import com.haki.hqrecipe.ui.theme.selectedItem
import com.haki.hqrecipe.util.Urbanist

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    val context = LocalContext.current

    val query by viewModel.query
    viewModel.getSearchRecipe(query)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(genBg)
    ) {
        MySearchBar(
            query = query,
            onQueryChange = viewModel::getSearchRecipe,
        )

        viewModel.resultState.collectAsState(initial = ResultState.Loading).value.let { result ->
            when (result) {
                is ResultState.Loading -> {
                    viewModel.getAllRecipe()
                }

                is ResultState.Success -> {
                    if (result.data.isEmpty()) {
                        Text(
                            modifier = modifier
                                .align(Alignment.Center)
                                .testTag("home_error"),
                            text = stringResource(id = R.string.error_home),
                            color = selectedItem,
                            fontFamily = Urbanist
                        )
                    } else {
                        HomeContent(
                            recipes = result.data,
                            modifier = modifier,
                            navigateToDetail = navigateToDetail,
                            onSave = { id ->
                                viewModel.saveRecipe(recipeId = id)
                                viewModel.getSearchRecipe(query)
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

@Composable
fun HomeContent(
    recipes: List<RecipeModel>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
    onSave: (id: Long) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.testTag("RecipeList")
    ) {
        item {
            Spacer(modifier = modifier.padding(top = 65.dp))
        }
        items(recipes, key = { it.id }) { data ->
            RecipeItem(
                photoUrl = data.photo,
                id = data.id,
                name = data.name,
                isSaved = data.isSaved,
                modifier = Modifier.clickable {
                    navigateToDetail(data.id)
                },
                onSave = onSave
            )
        }
    }

}
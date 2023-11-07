package com.haki.hqrecipe.screen.detail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.haki.hqrecipe.R
import com.haki.hqrecipe.ViewModelFactory
import com.haki.hqrecipe.data.ResultState
import com.haki.hqrecipe.di.Injection
import com.haki.hqrecipe.ui.theme.genBg
import com.haki.hqrecipe.ui.theme.selectedItem
import com.haki.hqrecipe.util.Urbanist


@Composable
fun DetailScreen(
    recipeId: Long,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
) {
    val context = LocalContext.current
    viewModel.resultState.collectAsState(initial = ResultState.Loading).value.let { resulState ->
        when (resulState) {
            is ResultState.Loading -> {
                viewModel.getRecipeById(recipeId)
            }

            is ResultState.Success -> {
                val data = resulState.data
                DetailContent(
                    data.name,
                    data.photo,
                    data.desc,
                    data.ingre,
                    data.steps,
                    onBackClick = navigateBack,
                )
            }

            is ResultState.Error -> {
                Toast.makeText(
                    context, stringResource(id = R.string.error), Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

@Composable
fun DetailContent(
    name: String,
    photoUrl: String,
    description: String,
    ingre: String,
    steps: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,

    ) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = modifier
                .fillMaxHeight(0.33f)
                .fillMaxWidth()
                .zIndex(1f)
        ) {
            AsyncImage(
                model = photoUrl,
                contentDescription = name,
                modifier = modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back),
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onBackClick() }
                    .clip(CircleShape)
                    .background(selectedItem)
                    .padding(5.dp),
                tint = genBg)

            ElevatedCard(
                modifier = modifier
                    .wrapContentWidth()
                    .align(Alignment.BottomCenter)
                    .offset(y = 40.dp)
                    .padding(start = 30.dp, end = 30.dp),
                colors = CardDefaults.cardColors(selectedItem)
            ) {
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(20.dp),
                    fontSize = 25.sp,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Bold,
                    color = genBg
                )
            }
        }
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(genBg)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
                .padding(top = 50.dp),
        ) {
            DetailComponent(section = stringResource(id = R.string.desc), value = description)
            DetailComponent(section = stringResource(id = R.string.ingre), value = ingre)
            DetailComponent(section = stringResource(id = R.string.steps), value = steps)
        }
    }
}

@Composable
fun DetailComponent(
    modifier: Modifier = Modifier, section: String, value: String
) {
    Text(
        text = section,
        fontWeight = FontWeight.Bold,
        fontFamily = Urbanist,
        fontSize = 22.sp,
        modifier = modifier.padding(bottom = 5.dp),
        color = Color.Black
    )
    Divider(
        color = Color.Black, thickness = 1.dp, modifier = modifier.padding(bottom = 5.dp)
    )

    Text(
        text = value,
        fontFamily = Urbanist,
        fontSize = 16.sp,
        modifier = modifier.padding(bottom = 16.dp),
        color = Color.Black
    )
}
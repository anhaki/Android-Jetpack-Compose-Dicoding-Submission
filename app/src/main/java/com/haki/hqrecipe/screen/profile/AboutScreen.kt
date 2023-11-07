package com.haki.hqrecipe.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haki.hqrecipe.R
import com.haki.hqrecipe.ui.theme.HqRecipeTheme
import com.haki.hqrecipe.ui.theme.genBg
import com.haki.hqrecipe.ui.theme.selectedItem
import com.haki.hqrecipe.util.Urbanist

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()){
        Image(
            modifier = modifier.fillMaxSize(),
            contentScale  = ContentScale.Crop,
            painter = painterResource(id = R.drawable.absurd),
            contentDescription = "About background")
        Column(modifier = modifier
            .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = modifier
                    .padding(start = 80.dp, end = 80.dp)
                    .border(5.dp, selectedItem, CircleShape)
                    .padding(10.dp)
                    .clip(CircleShape)
                    .background(selectedItem),
                painter = painterResource(id = R.drawable.dev_img),
                contentDescription = "Haki's Photo",
            )
            Text(
                modifier = modifier
                    .padding(top = 10.dp, bottom = 5.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(selectedItem)
                    .padding(10.dp),
                text = "Muhammad Anugrah Hakiki",
                color = genBg,
                fontFamily = Urbanist,
                fontSize = 20.sp
            )
            Text(
                text = "m.anugrahhakiki@gmail.com",
                color = selectedItem,
                fontFamily = Urbanist,
                fontSize = 14.sp
            )
        }
    }
}

@Preview
@Composable
fun ProfilePreview() {
    HqRecipeTheme {
        ProfileScreen()
    }
}
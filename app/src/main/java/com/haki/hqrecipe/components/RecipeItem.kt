package com.haki.hqrecipe.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.haki.hqrecipe.ui.theme.genBg
import com.haki.hqrecipe.ui.theme.indicatorColor
import com.haki.hqrecipe.ui.theme.selectedItem
import com.haki.hqrecipe.util.Urbanist
import com.haki.hqrecipe.R

@Composable
fun RecipeItem(
    modifier: Modifier = Modifier ,
    id: Long,
    name: String,
    photoUrl: String,
    isSaved: Boolean,
    onSave: (id: Long) -> Unit,
    ) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ){
            AsyncImage(
                model = photoUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .drawWithCache {
                        val gradient = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, selectedItem),
                            startY = size.height / 5,
                            endY = size.height
                        )
                        onDrawWithContent {
                            drawContent()
                            drawRect(gradient, blendMode = BlendMode.Multiply)
                        }
                    }
            )
            Icon(
                imageVector =
                if (isSaved) ImageVector.vectorResource(R.drawable.ic_bookmark_solid) else ImageVector.vectorResource(R.drawable.ic_bookmark_border),
                contentDescription = "Save",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(5.dp)
                    .clickable { onSave(id) },
                tint = genBg,
            )
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = Urbanist,
                fontWeight = FontWeight.Bold,
                color = genBg,
                text = name,
                fontSize = 25.sp
            )
        }
    }
}
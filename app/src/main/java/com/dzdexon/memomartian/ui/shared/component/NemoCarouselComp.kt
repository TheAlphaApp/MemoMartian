package com.dzdexon.memomartian.ui.shared.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.carousel.CarouselDefaults
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NemoCarouselComp(
    modifier: Modifier = Modifier,
    imageList: List<String>,
    height: Dp,
    shadowColor: Color,
    isShadowUpsideDown: Boolean = false
) {
    val carouselState = rememberCarouselState { imageList.count() }

    if (imageList.isNotEmpty())
        HorizontalMultiBrowseCarousel(
            state = carouselState,
            preferredItemWidth = 300.dp,
            modifier = modifier,
            itemSpacing = 1.dp,
            flingBehavior = CarouselDefaults.multiBrowseFlingBehavior(state = carouselState),
        ) { page ->
            Box(
                modifier = Modifier
                    .height(height)
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    modifier = Modifier
                        .matchParentSize(),
                    model = imageList[page],
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                colors = if (isShadowUpsideDown) listOf(
                                    shadowColor.copy(alpha = 1f),
                                    Color.Transparent,
                                ) else listOf(
                                    Color.Transparent,
                                    shadowColor.copy(alpha = 1f)
                                )
                            )
                        )
                )
            }

        }


}
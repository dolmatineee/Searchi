package com.example.animetinder.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.animetinder.viewmodels.SingleGameViewModel
import com.example.animetinder.customs.SwipeableMovieCard
import com.example.animetinder.factory.SingleGameViewModelFactory
import com.example.animetinder.ui.theme.BackgroundColor
import com.example.animetinder.ui.theme.SecondaryColor


@Composable
fun SingleGameScreen(
    paddingValues: PaddingValues,
    onCardClickListener: (Int) -> Unit,
    viewModelFactory: SingleGameViewModelFactory
) {
    val viewModel: SingleGameViewModel = viewModel(factory = viewModelFactory)
    val movie by viewModel.movie.observeAsState()
    val isLoading = viewModel.isLoading.value

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            val transition = rememberInfiniteTransition(label = "")
            val alpha by transition.animateFloat(
                initialValue = 0.3f,
                targetValue = 0.8f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1000, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                ), label = ""
            )

            Card(
                modifier = Modifier
                    .padding(all = 32.dp)
                    .fillMaxSize()
                    .graphicsLayer {
                        this.alpha = alpha
                    },
                elevation = CardDefaults.cardElevation(10.dp),
                shape = RoundedCornerShape(25.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(SecondaryColor)
                )
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(paddingValues)
        ) {
            movie?.let {
                SwipeableMovieCard(
                    movie = it,
                    onSwipeRight = {
                        viewModel.saveLikedMovie(it.id)
                        viewModel.getRandomMovie()
                    },
                    onSwipeLeft = {
                        viewModel.getRandomMovie()
                    },
                    onCardClickListener = {
                       onCardClickListener(movie!!.id)
                    }
                )
            }
        }

    }
}
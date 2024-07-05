package com.example.animetinder.customs

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.animetinder.data.Movie
import com.example.animetinder.ui.theme.BackgroundColor
import com.example.animetinder.ui.theme.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableMovieCard(movie: Movie, onSwipeRight: () -> Unit, onSwipeLeft: () -> Unit, onCardClickListener: (Movie) -> Unit) {
    val dismissState = rememberDismissState(
        confirmValueChange = {
            // Обработка свайпа
            when (it) {
                DismissValue.DismissedToEnd -> {
                    onSwipeRight()
                    true
                }
                DismissValue.DismissedToStart -> {
                    onSwipeLeft()
                    true
                }
                else -> false
            }
            true
        },
        positionalThreshold = { totalDistance -> totalDistance / 2.5f }
    )

    if (dismissState.currentValue != DismissValue.Default) {
        LaunchedEffect(movie) {
            dismissState.reset()
        }
    }



    val cardColor by animateColorAsState(
        when (dismissState.targetValue) {
            DismissValue.DismissedToEnd -> Color.Green.copy(alpha = 0.5f)
            DismissValue.DismissedToStart -> Color.Red.copy(alpha = 0.5f)
            else -> Color.Transparent
        }
    )


    SwipeToDismiss(
        state = dismissState,
        background = {
                     BackgroundColor
        },
        dismissContent = {
            MovieCard(movie, cardColor, onCardClickListener)
        },
        directions = setOf(
            DismissDirection.EndToStart,
            DismissDirection.StartToEnd
        )
    )
}

@Composable
fun MovieCard(movie: Movie, cardColor: Color, onCardClickListener: (Movie) -> Unit) {

    Card(
        modifier = Modifier
            .padding(all = 32.dp)
            .fillMaxSize()
            .clickable { onCardClickListener(movie) },
        elevation = CardDefaults.cardElevation(10.dp),
        shape = RoundedCornerShape(25.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = movie.poster.url,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                colorFilter = ColorFilter.tint(color = cardColor, blendMode = BlendMode.Darken)
            )
            GradientCard()

            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = movie.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )

                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(15.dp))
                        .wrapContentSize()
                        .background(PrimaryColor)
                        .padding(vertical = 6.dp, horizontal = 16.dp),

                ) {
                    Text(
                        text = "${movie.rating.imdb}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                }


                Text(
                    text = "${movie.year} • +${movie.ageRating} • ${movie.countries.joinToString { it.name }}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }
        }

    }
}


@Composable
fun GradientCard() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    0.05F to Color.Black,
                    0.3F to Color.Transparent
                )
            )

    )
}

/*@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MovieCardPreview(
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        MovieCard(
            movie = Movie(
                id = 1,
                name = "Марсианин",
                year = 2023,
                rating = Rating(9.6),
                poster = Poster("https://avatars.mds.yandex.net/get-kinopoisk-image/1900788/6f631486-e947-487d-94d6-41c2b5a8f5a0/1920x"),
                ageRating = "16",
                countries = listOf(Countries("США"))
            ),
            cardColor = Color.White.copy(alpha = 1f)
        )
    }

}*/
